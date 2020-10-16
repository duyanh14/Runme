<%@page contentType="text/html" pageEncoding="UTF-8"%>


<section class="bg-primary" id="about" style="padding-top: 120px;background-color: rgb(108, 117, 125);padding-bottom: 32px;">
    <div class="container">
        <div class="row"> 
            <div class="col-lg-8 mx-auto text-center">  
                <h2 class="section-heading text-white">Thank you for downloading</h2>
                <hr class="light my-4">
                <p class="text-faded mb-4">If the download does not start, please click <a id="btndownload" href="/download/apk">in here</a> to try again</p>
            </div>
        </div>
    </div>
</section>

<section id="help">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <i class="fas fa-download fa-w-16 fa-9x"></i>
                <hr class="my-4">
            </div>
        </div>
    </div>

</section>




<script>

    function sleep(seconds) {
        var waitUntil = new Date().getTime() + seconds * 1000;
        while (new Date().getTime() < waitUntil)
            true;
    }

    function download() {

        document.getElementById('btndownload').click();
    }

    $(document).ready(function () {
        // Usage!
        setTimeout(function () {
            download();
        }, 2001);
    });

</script>