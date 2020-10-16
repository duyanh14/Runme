



$aucard_fullscreen = false;

function aucard_fullscreen($this){
  $find = $('.au-card') ;
  var aucard_fullscreen = document.getElementById("aucard_fullscreen");

  if ( $aucard_fullscreen) {
      $find.css("border-radius", "10px");
      $(".header-desktop").css("display", "block");
      $($this).find( "i" ).attr('class', 'fas fa-expand-arrows-alt');
      $aucard_fullscreen = false;
      exitFullScreen();

  } else {
    requestFullScreen(aucard_fullscreen);

    $find.css("border-radius", "unset");

     $(".header-desktop").css("display", "none");
      $aucard_fullscreen = true;
      $($this).find( "i" ).attr('class', 'fas fa-compress-arrows-alt');



    }
    $find.toggleClass('fullscreen'); 

}

function isFullScreen()
{
    return (document.fullScreenElement && document.fullScreenElement !== null)
         || document.mozFullScreen
         || document.webkitIsFullScreen;
}


function requestFullScreen(element)
{
    if (element.requestFullscreen)
        element.requestFullscreen();
    else if (element.msRequestFullscreen)
        element.msRequestFullscreen();
    else if (element.mozRequestFullScreen)
        element.mozRequestFullScreen();
    else if (element.webkitRequestFullscreen)
        element.webkitRequestFullscreen();
}

function exitFullScreen()
{
    if (document.exitFullscreen)
        document.exitFullscreen();
    else if (document.msExitFullscreen)
        document.msExitFullscreen();
    else if (document.mozCancelFullScreen)
        document.mozCancelFullScreen();
    else if (document.webkitExitFullscreen)
        document.webkitExitFullscreen();
}

function toggleFullScreen(element)
{
    if (isFullScreen())
        exitFullScreen();
    else
        requestFullScreen(element || document.documentElement);
}

function aucard_back(){
    $( ".au-inbox-wrap" ).removeClass( "show-chat-box" );
    $( ".nick a" ).remove();
    $( ".au-chat__content" ).empty();
}