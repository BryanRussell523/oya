function scaleBodyWidthAndHeight()
{
    if(WEBVIEW_RECEIVED_OS == 'ios') // Adjusting for IPAD (OS =  ios)
    {
        // On iPad, the html body is slightly larger than the viewport
        if( typeof window.innerWidth != "undefined" &&
            (document.body.style.width != window.innerWidth || document.body.style.height != window.innerHeight))
        {
            document.body.style.width = window.innerWidth + 'px';
            document.body.style.height = window.innerHeight + 'px';
        }
    }
}

function foobar_cont(){
    //console.log("Finished.");
};

function sleep(millis, callback)
{
    setTimeout(function() {
        callback();
    }, millis);
}

function showLoading(blnShow) {

    viewportDimensions = getViewportDimensions();

    if (blnShow)
    {
        var currentViewportHeight = viewportDimensions['height'];
        var currentViewportWidth = viewportDimensions['width'];

        
        LOADING_ELEM.style.width = currentViewportWidth + 'px';
        LOADING_ELEM.style.height = currentViewportHeight + 'px';
        LOADING_ELEM.style.display = 'table';
    }
    else
    {
        LOADING_ELEM.style.display = 'none';
    }
}

function adjustLoading()
{
    viewportDimensions = getViewportDimensions();

    var currentViewportHeight = viewportDimensions['height'];
    var currentViewportWidth = viewportDimensions['width'];

    
    LOADING_ELEM.style.width = currentViewportWidth + 'px';
    LOADING_ELEM.style.height = currentViewportHeight + 'px';

    setTimeout("adjustLoading()", 50);
}

// Getting areas;
function getAreas()
{
    IMG_AREAS = {};

    if(typeof IMG_ELEM_AREA_LANDSCAPE != "undefined" && IMG_ELEM_AREA_LANDSCAPE)
    {
        var arrayAreas = IMG_ELEM_AREA_LANDSCAPE;
        IMG_AREAS[IMG_ELEM_LANDSCAPE_TAG] = {};
        for(var i = 0; i < arrayAreas.length; i++)
        {
            IMG_AREAS[IMG_ELEM_LANDSCAPE_TAG][i] = arrayAreas[i].coords;
        }
    }

}

// Scale areas
function scaleAreas()
{
    var rescale = IMG_RESIZED_WIDTH / IMG_WIDTH;

    //console.log(rescale);

    var arrayAreas = IMG_ELEM_AREA;

    for(var i = 0; i < arrayAreas.length; i++)
    {
        var sarray = IMG_AREAS[IMG_ELEM_AREA_TAG][i].split(","); // convert coordinates to a numeric array assuming comma-delimited values
        //console.log(sarray);
        var rarray =new Array();
        for(var j = 0; j < sarray.length; j++)
        {
            // rescale the values
            rarray[j]=Math.round(parseInt(sarray[j]) * rescale);
        }
        if(arrayAreas[i].href !== 'exit:') {
			generatePriceButton(rarray);
        }
        arrayAreas[i].coords=rarray.join(","); // put the values back into a string
        //console.log(arrayAreas[i].coords);
    }
}


// display method, basically starts the processing
var display = function() {

    // Fetching various data
    webviewFetcher();

    // Body fixes (OS specific)
    scaleBodyWidthAndHeight(); // re-add if needed, shouldn't be needed in the new processing

    // Set details for actual image used
    orientationBasedAdjust();

    if (isImgLoaded(IMG_ELEM) == false){
        setTimeout("display()", 500);
        return;
    }

    // Fetching areas and loading them into a global object
    getAreas();

    // Adjustment of splashscreen (initial call)
    adjustSplashScreen();

    // Showing the splashscreen
    IMG_ELEM.style.display = 'block';
    IMG_ELEM.style.visibility = 'visible';
    
    areaListenersInit();

    showLoading(false);
}

// onLoad - not all resources may be ready.
var load = function(){
    if (document.addEventListener){
        document.addEventListener('touchmove', function(e){
            e.preventDefault();
        }, false);
    }
    else{
        document.attachEvent('ontouchmove', function(e){
            e.preventDefault();
        });
    }

    // There must be some delay in order for the loading screen to show up.
    // TO DO: verify this, why is it needed ? (ulterior testing/tweaking)
    setTimeout("display()", 200);
}

// simple algorithm to determine if element was loaded
function isImgLoaded(elem)
{
    // TO DO: Improve loading detection algorithm (if possible)

    if(!elem)
    {
        return false;
    }

    if(!elem.complete) //Some browsers report incorrectly that the image is completed when it is not but always report correctly when it is not loaded
    {
        return false;
    }

    if (typeof elem.naturalWidth != "undefined" && elem.naturalWidth == 0) // IE does not have naturalWidth
    {
        return false;
    }

    return true;// no other way of checking.. assume it's ok
}

function orientationBasedAdjust()
{
    // No adjustment can or needs to be done
    return true;
}

function getResizeType()
{
    viewportDimensions = getViewportDimensions();

    var currentViewportHeight = viewportDimensions['height'];
    var currentViewportWidth = viewportDimensions['width'];

    if(IMG_HEIGHT / currentViewportHeight > IMG_WIDTH / currentViewportWidth)
    {
        $resize_type = "height";
    }
    else
    {
        $resize_type = "width";
    }

    return $resize_type;
}

function adjustSplashScreen()
{
    viewportDimensions = getViewportDimensions();

    var currentViewportHeight = viewportDimensions['height'];
    var currentViewportWidth = viewportDimensions['width'];
    var currentViewportHeightPadding = viewportDimensions['paddingHeight'];
    var currentViewportWidthPadding = viewportDimensions['paddingWidth'];

    window.scrollTo(0,0);

            if(WEBVIEW_RECEIVED_OS == 'WP8' || WEBVIEW_RECEIVED_OS == 'windows')
        {
            document.body.style.backgroundColor = '#000000';
        }
    
    if(currentViewportHeight != STORED_VIEWPORT_HEIGHT || currentViewportWidth != STORED_VIEWPORT_WIDTH)
    {
        orientationBasedAdjust();

        imageRatio = IMG_WIDTH / IMG_HEIGHT;
        currentViewRatio = currentViewportWidth / currentViewportHeight;

        if(currentViewRatio > imageRatio)
        {
            IMG_RESIZED_WIDTH = IMG_WIDTH * currentViewportHeight / IMG_HEIGHT;
            IMG_RESIZED_HEIGHT = currentViewportHeight;
                    }
        else
        {
            IMG_RESIZED_WIDTH = currentViewportWidth;
            IMG_RESIZED_HEIGHT = IMG_HEIGHT * currentViewportWidth / IMG_WIDTH;
                    }

        IMG_RESIZED_HEIGHT = Math.ceil(IMG_RESIZED_HEIGHT);
        IMG_RESIZED_WIDTH = Math.ceil(IMG_RESIZED_WIDTH);
		var divContainer = document.getElementById("imgContainer");
		divContainer.style.width = IMG_RESIZED_WIDTH + 'px';
        
        // Resize Type based adjust
        /*resizeType = getResizeType();
         if(resizeType == "width")
         {
         IMG_ELEM.style.width = '100%';
         IMG_ELEM.style.height = 'auto';
         }
         else
         {
         IMG_ELEM.style.width = 'auto';
         IMG_ELEM.style.height = '100%';
         }*/

        // Dynamically setting image dimensions
        IMG_ELEM.style.width = IMG_RESIZED_WIDTH + 'px';
        IMG_ELEM.style.height = IMG_RESIZED_HEIGHT + 'px';

        // Aligning to center
        img_top = Math.round((currentViewportHeight - Math.round(parseInt(IMG_ELEM.style.height))) / 2);
        img_left = Math.round((currentViewportWidth - Math.round(parseInt(IMG_ELEM.style.width))) / 2);
        //img_top = 0;
        //img_left = -20;
        //IMG_ELEM.style.top =  img_top + 'px';
        //IMG_ELEM.style.left =  img_left + 'px';
        //IMG_ELEM.style.position = "absolute";
        //IMG_ELEM.style.marginLeft = '20px';

        // Storing new dimensions
        STORED_VIEWPORT_HEIGHT = currentViewportHeight;
        STORED_VIEWPORT_WIDTH = currentViewportWidth;

        scaleAreas();
        //scaleBodyWidthAndHeight();
    }

    setTimeout("adjustSplashScreen()", 100);
}

function redir(strlink)
{
    if (!isFirstClick) return;

    if(WEBVIEW_RECEIVED_OS == 'WP8' || WEBVIEW_RECEIVED_OS == 'windows') // Adjusting for WINDOWS (OS =  windows)
    {
        window.external.notify('Initialize redir.');
    }

    showLoading(true);

    isFirstClick = false;

    //Auto-closing after 10 seconds, if redirect error.
    setTimeout("autoClose('Redirect not completed (request too long).')", '10000');

    // Check for Internet connection first
    // This is required only for external links (not used currently)
    if (strlink.indexOf("http") == 0 && 0)
    {
        // No logic here yet; no need;
    }
    else {
        setTimeout(function(){
            goToLink(strlink);
        }, 500);
    }
}

function getByAttribute(tagType, tagAttribute, tagAttributeValue)
{
    var elements = [];
    var tagList = document.getElementsByTagName(tagType);
    tagListLength = tagList.length;
    for(var i = 0; i < tagListLength; i++)
    {
        if(tagList[i].getAttribute(tagAttribute) == tagAttributeValue)
        {
            elements.push(tagList[i]);
        }
    }

    return elements;
}

function SetBackgroundColor(r, g, b, a)
{
    var color = 'rgba('+r+','+g+','+b+','+a+')';
    document.body.style.backgroundColor = color;
}

////////////////////
// Global Variables
////////////////////

var LOADING_ELEM = document.getElementById('loading');
var IMG_AREAS = new Array();
var isFirstClick = true;

// Image settings for landscape and/or potrait

// Landscape
var IMG_WIDTH_LANDSCAPE = 1520; // set on prerendering
var IMG_HEIGHT_LANDSCAPE = 900; // set on prerendering
var IMG_ELEM_LANDSCAPE = document.getElementById('splashImage_landscape');
var IMG_ELEM_AREA_LANDSCAPE = document.getElementsByName("splashscreen_landscape_area");
if(IMG_ELEM_AREA_LANDSCAPE.length == 0)
{
    IMG_ELEM_AREA_LANDSCAPE = getByAttribute('area', 'name', 'splashscreen_landscape_area');
}
var IMG_ELEM_LANDSCAPE_TAG = 'landscape';


// Core IMG details - will be adapted on the fly based on detected ratio (if both landscape and portrait are available)
var IMG_HEIGHT = 900;
var IMG_WIDTH = 1520;
var IMG_ELEM = IMG_ELEM_LANDSCAPE;
var IMG_ELEM_AREA = IMG_ELEM_AREA_LANDSCAPE;
var IMG_ELEM_AREA_TAG = IMG_ELEM_LANDSCAPE_TAG;

// Resized width/height holders
var IMG_RESIZED_WIDTH = 0;
var IMG_RESIZED_HEIGHT = 0;

// Globals to hold stored viewport dimensions
STORED_VIEWPORT_HEIGHT = 0;
STORED_VIEWPORT_WIDTH = 0;

// show loader
adjustLoading(); // forced adjustments in loop to make up for strange behavior
// don't show loading animation during zoom
    showLoading(true);



setTimeout(function() {
    if (isImgLoaded(IMG_ELEM_LANDSCAPE) == false) {
        autoClose('Timeout (resource not ready (landscape)).');
    }
}, 10000);

if (window.addEventListener) //Webkit
{
    window.addEventListener("load", load, false);
}
else //Windows
{
    window.attachEvent("onload", load);
}

