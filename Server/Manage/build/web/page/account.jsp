<%@page import="com.dn.amonir.model.Request"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<% Request Request9 = (Request) request.getAttribute("Request");%>

<style>

    @media (max-width: 767px){
        .dropdown {
            display: block !important;
        }
        #myTab {
            display:none;
        }
        #fgh435gfhg {
            border: 0px !important;
        }
    }
</style>
<div class="row">
    <div class="col-lg-12">
        <div class="card">
            <div class="card" style="margin-bottom: unset;">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-12">
                <div class="card">
                  <div class="card-header">
                    <strong class="card-title">3D Switch</strong>
                  </div>
                  <div class="card-body">
                    <label class="switch switch-3d switch-primary mr-3">
                      <input type="checkbox" class="switch-input" checked="true">
                      <span class="switch-label"></span>
                      <span class="switch-handle"></span>
                    </label>
                    <label class="switch switch-3d switch-secondary mr-3">
                      <input type="checkbox" class="switch-input" checked="true">
                      <span class="switch-label"></span>
                      <span class="switch-handle"></span>
                    </label>
                    <label class="switch switch-3d switch-success mr-3">
                      <input type="checkbox" class="switch-input" checked="true">
                      <span class="switch-label"></span>
                      <span class="switch-handle"></span>
                    </label>
                    <label class="switch switch-3d switch-warning mr-3">
                      <input type="checkbox" class="switch-input" checked="true">
                      <span class="switch-label"></span>
                      <span class="switch-handle"></span>
                    </label>
                    <label class="switch switch-3d switch-info mr-3">
                      <input type="checkbox" class="switch-input" checked="true">
                      <span class="switch-label"></span>
                      <span class="switch-handle"></span>
                    </label>
                    <label class="switch switch-3d switch-danger mr-3">
                      <input type="checkbox" class="switch-input" checked="true">
                      <span class="switch-label"></span>
                      <span class="switch-handle"></span>
                    </label>

                  </div>
                </div>
              </div>
                        <div class="col-md-2 mb-3" style="border-right: 1px solid #dee2e6;margin-bottom: 0px !important;" id="fgh435gfhg">

                            <select class="dropdown" id="navselect" onchange="$('#myTab li #' + this.value + '-tab').trigger('click');" style="display:none;width: 100%;height: 42px;text-align-last: center;margin-bottom: 16px !important;">
                                <option value="overview" >Overview</option>
                                <option value="change-password">Change password</option>
                            </select>


                            <ul class="nav nav-pills flex-column" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="overview-tab" onClick="$('#navselect').val('overview');" data-toggle="tab" href="#overview" role="tab" aria-controls="home" aria-selected="true">Overview</a>
                                </li>

                                <li class="nav-item">
                                    <a class="nav-link" id="change-password-tab" onClick="$('#navselect').val('change-password');" data-toggle="tab" href="#change-password" role="tab" aria-controls="contact" aria-selected="false">Change password</a>
                                </li>
                            </ul>
                        </div>
                        <!-- /.col-md-4 -->
                        <div class="col-md-10">
                            <div class="tab-content" style=" border: unset !important;" id="myTabContent">
                                <div class="tab-pane fade show active" id="overview" role="tabpanel" aria-labelledby="home-tab">

                                    <div class="row">
                                        <div class="col-lg-6" style="padding-right: 1px;" id="gfhgfoi53hou4ehrdgiufdg ">
                                            <section class="card" style="border: 0px;margin-bottom:0px;" id="ghj54oi6htouigfdghfd">
                                                <div class="card">
                                                    <div class="card-header">Information</div>
                                                    <div class="card-body card-block" style="">
                                                        <form action="" method="post" enctype="multipart/form-data" class="form-horizontal">

                                                            <div class="row form-group">
                                                                <div class="col col-md-3">
                                                                    <label for="text-input" class=" form-control-label">Name</label>
                                                                </div>
                                                                <div class="col-12 col-md-9">
                                                                    <input type="text" id="text-input" name="text-input" value=" <%= Request9.Account.Name%>" class="form-control" disabled>
                                                                    <!--<small class="form-text text-muted">This is a help text</small>-->
                                                                </div>
                                                            </div>

                                                            <div class="row form-group">
                                                                <div class="col col-md-3">
                                                                    <label for="text-input" class=" form-control-label">Email</label>
                                                                </div>
                                                                <div class="col-12 col-md-9">
                                                                    <input type="text" id="text-input" name="text-input" value=" <%= Request9.Account.Email%>" class="form-control" disabled>
                                                                    <!--<small class="form-text text-muted">This is a help text</small>-->
                                                                </div>
                                                            </div>

                                                            <div class="row form-group" style="margin-bottom: 0px;">
                                                                <div class="col col-md-3">
                                                                    <label for="text-input" class=" form-control-label">  Phone number</label>
                                                                </div>
                                                                <div class="col-12 col-md-9">
                                                                    <input type="text" id="text-input" name="text-input" value=" <%=Request9.Account.Phone.Number%>" class="form-control" disabled>
                                                                    <!--<small class="form-text text-muted">This is a help text</small>-->
                                                                </div>
                                                            </div>


                                                        </form>
                                                    </div>
                                                    <div class="card-footer">
                                                        <button type="submit" class="btn btn-primary btn-sm" style="width:100px">
                                                            <i class="fa fa-dot-circle"></i>&nbsp&nbspSubmit
                                                        </button>

                                                    </div>
                                                </div>
                                            </section>
                                        </div>
                                        <div class="col-lg-6 ">

                                            <div class="card">
                                                <div class="card-header">Package</div>
                                                <div class="card-body card-block" style="">
                                                    <form action="" method="post" enctype="multipart/form-data" class="form-horizontal">

                                                        <div class="row form-group">
                                                            <div class="col col-md-3">
                                                                <label for="text-input" class=" form-control-label">Name</label>
                                                            </div>
                                                            <div class="col-12 col-md-9">
                                                                <input type="text" id="text-input" name="text-input" placeholder="Text" class="form-control">
                                                            </div>
                                                        </div>

                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                                <div class="tab-pane fade" id="change-password" role="tabpanel" aria-labelledby="contact-tab">
                                    <h2>Contact</h2>
                                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Neque, eveniet earum. Sed accusantium eligendi molestiae quo hic velit nobis et, tempora placeat ratione rem blanditiis voluptates vel ipsam? Facilis, earum!</p>

                                </div>
                            </div>
                        </div>
                        <!-- /.col-md-8 -->
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
                var list = obj.list;
                for (var i = 0; i < list.length; i++) {
                    type = list[i].type;
                    link = "/request/media.php?action=view&mid=" + list[i].id;
                    if ((type == "jpg") || (type == "png") || (type == "jpeg")) {
                        $(".grid").append('<div class="item item--medium" style="background-image: url(' + "'" + link + "'" + ');"><div class="item__details">' + list[i].time + '</div></div>');
                    } else if (type == "mp4") {
                        $(".grid").append('<div class="item item--medium" style="display: block;" > <video  controls style=" margin-bottom: -12px;  width: 100%;height: 84%;"><source src="' + link + '" type="video/mp4" ></video>      <div class="item__details">' + list[i].time + '</div></div>');
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
        /* box-shadow: -2px 2px 10px 0px rgba(68, 68, 68, 0.4); */
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
        /* box-shadow: -2px 2px 10px 0px rgba(68, 68, 68, 0.4); */
        /* transition: -webkit-transform 0.3s ease-in-out; */
        /* transition: transform 0.3s ease-in-out; */
        /* transition: transform 0.3s ease-in-out, -webkit-transform 0.3s ease-in-out; */
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
        /* transition: opacity 0.3s ease-in-out; */
    }
    .item:hover {
        /* -webkit-transform: scale(1.05); */
        /* transform: scale(1.05); */
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