const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();
const chatsRef = admin.database().ref('/chats');

/**
 * Cloud Function triggered when a new message is added to a chat.
 *
 * @param {Object} snapshot - The data snapshot of the newly created message.
 * @param {Object} context - The context containing information about the event.
 * @returns {Promise<null>} - Resolves when the notification is sent successfully.
 */
exports.sendChatNotification = functions.database.ref('/chats/{chatId}/messages/{messageId}')
    .onCreate(async (snapshot, context) => {
      const message = snapshot.val();
      const chatId = context.params.chatId;

      try {
        const recipientUid = await getRecipientUid(chatId, message.senderUid);
        const recipientToken = await getRecipientToken(recipientUid);

        // Sending the notification
        await sendNotification(recipientToken, 'New Message', 'You have a new message', message.senderName);

        const payload = {
          notification: {
            title: 'New Message',
            body: `You have a new message from ${message.senderName}`,
          },
        };

        return admin.messaging().sendToTopic(recipientUid, payload);
      } catch (error) {
        console.error('Error:', error);
        return null; // Create custom error handling later on
      }
    });

/**
 * Retrieves the recipient's UID from the chat participants.
 *
 * @param {string} chatId - ID of the chat.
 * @param {string} senderUid - UID of the message sender.
 * @return {Promise<string>} - Resolves with the recipient's UID.
 * @throws {Error} - If there's an issue fetching the recipient UID.
 */
async function getRecipientUid(chatId, senderUid) {
  try {
    const chatSnapshot = await chatsRef.child(chatId).once('value');
    const chatData = chatSnapshot.val();

    if (!chatData || !chatData.participants) {
      throw new Error('Invalid chat or missing participants.');
    }

    const participantsUids = Object.keys(chatData.participants);
    const recipientUid = participantsUids.find((uid) => uid !== senderUid);

    if (!recipientUid) {
      throw new Error('Recipient UID not found.');
    }

    return recipientUid;
  } catch (error) {
    console.error('Error fetching recipient UID:', error);
    throw error;
  }
}

/**
 * Retrieves the FCM token of the recipient.
 *
 * @param {string} recipientUid - UID of the recipient.
 * @return {Promise<string>} - Resolves with the FCM token.
 * @throws {Error} - If there's an issue fetching the recipient's FCM token.
 */
async function getRecipientToken(recipientUid) {
  try {
    // Assuming you have a 'users' node in your database with FCM tokens
    const userSnapshot = await admin.database().ref(`/users/${recipientUid}/fcmToken`).once('value');
    const fcmToken = userSnapshot.val();

    if (!fcmToken) {
      throw new Error('FCM token not found for recipient.');
    }

    return fcmToken;
  } catch (error) {
    console.error('Error fetching recipient\'s FCM token:', error);
    throw error;
  }
}

/**
 * Sends a notification to the recipient's device using FCM.
 *
 * @param {string} recipientToken - FCM token of the recipient's device.
 * @param {string} title - Notification title.
 * @param {string} body - Notification body.
 * @param {string} senderName - Name of the message sender.
 * @return {Promise<void>} - Resolves when the notification is sent successfully.
 * @throws {Error} - If there's an issue sending the notification.
 */
async function sendNotification(recipientToken, title, body, senderName) {
  try {
    const payload = {
      notification: {
        title: title,
        body: `${body} from ${senderName}`,
      },
    };

    await admin.messaging().sendToDevice(recipientToken, payload);
  } catch (error) {
    console.error('Error sending notification:', error);
    throw error;
  }
}
