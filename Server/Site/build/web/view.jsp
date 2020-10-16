<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Amonir</title>
        <link rel="shortcut icon" type="image/x-icon" href="/img/fav.ico" />

        <!-- Bootstrap core CSS -->
        <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom fonts for this template -->
        <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic" rel="stylesheet" type="text/css">

        <!-- Plugin CSS -->
        <link href="vendor/magnific-popup/magnific-popup.css" rel="stylesheet">


        <!-- Custom styles for this template -->
        <link href="css/creative.css" rel="stylesheet">

        <script type="text/javascript" src="js/jquery.min.js"></script>


    </head>


    <body id="page-top">

        <!-- Navigation -->
        <nav class="navbar navbar-expand-lg navbar-light fixed-top navbar-shrink ${name == "home" ? "mnsp" : ""}" id="mainNav">
            <div class="container" >           
                <a class="navbar-brand js-scroll-trigger" href="./"><img src="/img/logo.png" style="height: 47px;"></a>
                <button class="navbar-toggler navbar-toggler-right collapsed" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="${name == "home" ? "#feature" : "./#feature"}">Feature</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="${name == "home" ? "#price" : "./#price"}">Price</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger ${name == "download" ? "active" : ""}" href="/download">Download</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger ${name == "help" ? "active" : ""}" href="/help">Help</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger ${name == "terms" ? "active" : ""}" href="/terms">Terms</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger ${name == "contact" ? "active" : ""}" href="/contact">Contact</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link js-scroll-trigger" href="http://manage.amonir.com">Manage</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!--         Masthead 
                <header class="masthead">
                    <div class="container h-100">
                        <div class="row h-100 align-items-center justify-content-center text-center">
                            <div class="col-lg-10 align-self-end">
                                <h1 class="text-uppercase text-white font-weight-bold">Your Favorite Source of Free Bootstrap Themes</h1>
                                <hr class="divider my-4">
                            </div>
                            <div class="col-lg-8 align-self-baseline">
                                <p class="text-white-75 font-weight-light mb-5">Start Bootstrap can help you build better websites using the Bootstrap framework! Just download a theme and start customizing, no strings attached!</p>
                                <a class="btn btn-primary btn-xl js-scroll-trigger" href="#about">Find Out More</a>
                            </div>
                        </div>
                    </div>
                </header>-->


        <jsp:include page="./view/${name}.jsp"></jsp:include>


        <!-- Footer -->
        <!--        <footer class="bg-light py-5">
                    <div class="container">
                        <div class="small text-center text-muted">Copyright &copy; 2019 - Start Bootstrap</div>
                    </div>
                </footer>-->

        <!-- Bootstrap core JavaScript -->
        <script src="vendor/jquery/jquery.min.js"></script>
        <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="/vendor/scrollreveal/scrollreveal.js"></script>

        <!-- Plugin JavaScript -->
        <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
        <script src="vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

        <!-- Custom scripts for this template -->
        <script src="js/creative.js"></script>

    </body>

    <script>


// to top right away
        if (window.location.hash)
            scroll(0, 0);
// void some browsers issue
        setTimeout(function () {
            scroll(0, 0);
        }, 3);

        $(function () {

            // your current click function
            $('.scroll').on('click', function (e) {
                e.preventDefault();
                $('html, body').animate({
                    scrollTop: $($(this).attr('href')).offset().top - 74
                }, 1000, 'swing');
            });

            // *only* if we have anchor on the url
            if (window.location.hash) {

                // smooth scroll to the anchor id
                $('html, body').animate({
                    scrollTop: $(window.location.hash).offset().top - 74
                }, 1000, 'swing');
            }

        });
    </script>

</html>
