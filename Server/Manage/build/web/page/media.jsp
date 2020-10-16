<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="row">
    <div class="col-lg-12">
        <div class="card">
            <div class="card" style="margin-bottom: unset;">
                <div class="card-body">
                    <div class="row">

                        <div class="col-sm-12" style="" id="ghj54oi6htouigfdghfd">

                            <div class="message">
                                Sorry, this browser is not supported.
                            </div>

                            <div class="grid">


                            </div>

                            <br>


                            <div class="col col-lg-12" style="padding-top: 1px;padding-bottom: 10px;">
                                <section class="card" style="
                                         background: transparent;
                                         border: none;
                                         ">
                                    <button id="hrytglfdkhgjlk4543" type="button" class="btn btn-success" onclick="load();" style="
                                            width: 50%;
                                            max-width: 400px;
                                            margin: 0 auto;height:46px" disabled>
                                        Show more</button>
                                </section>


                            </div>


                        </div>
                        <!--                        <div class="col-sm-4">
                                                    <section class="card" style="border: 0px; margin-bottom: unset;">
                        
                                                        <div class="custom-tab">
                                                            <nav>
                                                                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                                                    <a class="nav-item nav-link active show" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home" role="tab" aria-controls="custom-nav-home" aria-selected="false">
                                                                        <i class="fas fa-info" style="margin-right: 10px;">
                                                                        </i>Details
                                                                    </a>
                                                                     <a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile" aria-selected="false"><i class="fas fa-archive" style="margin-right: 10px;"></i>Lưu trữ</a> 
                                                                </div>
                                                            </nav>
                                                            <div class="tab-content pl-3 pt-2" id="nav-tabContent" style="padding-left: 0px !important;">
                                                                <div class="tab-pane fade active show" id="custom-nav-home1" role="tabpanel" aria-labelledby="custom-nav-home-tab1">
                                                                    <table class="table table-hover table-striped table-align-middle mb-0" style="margin-top:-8px;">
                                                                        <tbody>
                                                                            <tr>
                                                                                <td style="border-top: unset;">
                                                                                    Total of SMS
                                                                                </td>
                                                                                <td style="border-top: unset;">
                                                                        <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                                                                        </basix-switch>
                                                                        </td>
                                                                        <td style="border-top: unset;">
                        <%--<%= sms_get.get("TotalOfSMS")%>--%>

                    </td>
                    </tr>

                    <tr>
                        <td style="border-top: unset;">
                            Total of SMS content
                        </td>
                        <td style="border-top: unset;">
                    <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                    </basix-switch>
                    </td>
                    <td style="border-top: unset;">
                        <%--<%= sms_get.get("TotalOfSMSContent")%>--%>

                    </td>
                    </tr>

                    <tr>
                        <td style="border-top: unset;">
                            Total of SMS content sent
                        </td>
                        <td style="border-top: unset;">
                    <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                    </basix-switch>
                    </td>
                    <td style="border-top: unset;">
                        <%--<%= sms_get.get("TotalOfSMSContentSent")%>--%>

                    </td>
                    </tr>

                    <tr>
                        <td style="border-top: unset;">
                            Total of SMS content received
                        </td>
                        <td style="border-top: unset;">
                    <basix-switch type="3d" variant="primary" size="lg" :checked="true">
                    </basix-switch>
                    </td>
                    <td style="border-top: unset;">
                        <%--<%= sms_get.get("TotalOfSMSContentReceived")%>--%>

                    </td>
                    </tr>


                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>-->
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>



<script>
    $page = 1;

    load();

    function load() {

        $("#hrytglfdkhgjlk4543").attr("disabled", true);


        var data = {
            'Page': $page,
            'Limit': 10
        };

        $.ajax({
            url: "./Device/Media/Get",
            type: "post",
            data: data,
            success: function (result) {

                $page = $page + 1;

                var obj = JSON.parse(result);


                $("#hrytglfdkhgjlk4543").attr("disabled", !obj.IsNextPage);


                var list = obj.List;
                for (var i = 0; i < list.length; i++) {
                    type = list[i].Type;
                    link = "./Device/Media/View/?ID=" + list[i].ID;
                    if ((type == "jpg") || (type == "png") || (type == "jpeg")) {
                        $(".grid").append('<div class="item item--medium" style="background-image: url(' + "'" + link + "'" + ');"><div class="item__details">' + list[i].Time + '</div></div>');
                    } else if (type == "mp4") {
                        $(".grid").append('<div class="item item--medium" style="display: block;" > <video  controls style=" margin-bottom: -12px;  width: 100%;height: 84%;"><source src="' + link + '" type="video/mp4" ></video>      <div class="item__details">' + list[i].Time + '</div></div>');
                    }

                }

            }
        });
    }

</script>

<style class="cp-pen-styles">
    @import url("https://fonts.googleapis.com/css?family=Arvo");

    @supports (display: grid) {
        body,
        html {
            display: block;
        }
    }

    .message {
        border: 1px solid #d2d0d0;
        padding: 2em;
        font-size: 1.7vw;
         box-shadow: -2px 2px 10px 0px rgba(68, 68, 68, 0.4); 
    }
    @supports (display: grid) {
        .message {
            display: none;
        }
    }

    .section {
        display: none;
        padding: 2rem;
    }
    @media screen and (min-width: 768px) {
        .section {
            padding: 4rem;
        }
    }
    @supports (display: grid) {
        .section {
            display: block;
        }
    }

    h1 {
        font-size: 2rem;
        margin: 0 0 1.5em;
    }

    .grid {
        display: grid;
        grid-gap: 30px;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        grid-auto-rows: 150px;
        grid-auto-flow: row dense;
    }

    .item {
        position: relative;
        display: flex;
        flex-direction: column;
        justify-content: flex-end;
        box-sizing: border-box;
        color: #fff;
        grid-column-start: auto;
        grid-row-start: auto;
        color: #fff;
        background-size: cover;
        background-position: center;
         box-shadow: -2px 2px 10px 0px rgba(68, 68, 68, 0.4); 
         transition: -webkit-transform 0.3s ease-in-out; 
         transition: transform 0.3s ease-in-out; 
         transition: transform 0.3s ease-in-out, -webkit-transform 0.3s ease-in-out; 
        cursor: pointer;
        counter-increment: item-counter;
    }

    .item:after {
        content: '';
        position: absolute;
        width: 100%;
        height: 100%;
        /* background-color: black; */
        opacity: 0.3;
         transition: opacity 0.3s ease-in-out; 
    }
    .item:hover {
         -webkit-transform: scale(1.05); 
         /*transform: scale(1.05);*/ 
    }
    .item:hover:after {
        opacity: 0;
    }
    .item--medium {
        grid-row-end: span 2;
    }
    .item--large {
        grid-row-end: span 3;
    }
    .item--full {
        grid-column-end: auto;
    }
    @media screen and (min-width: 768px) {
        .item--full {
            grid-column: 1/-1;
            grid-row-end: span 2;
        }
    }
    .item__details {
        position: relative;
        z-index: 1;
        padding: 15px;
        color: #444;
        background: #fff;
        text-transform: lowercase;
        letter-spacing: 1px;
        color: #828282;
    }
    .item__details:before {
        content: counter(item-counter);
        font-weight: bold;
        font-size: 1.1rem;
        padding-right: 0.5em;
        color: #444;
    }
</style>