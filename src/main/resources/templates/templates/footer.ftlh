<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
</head>
<body>
<div class="footer footer-start">
    <div class="container">
        <div class="row" style="text-align:center;">
            <h2><@spring.message code="footer.listen.cache" /></h2>
        </div>
        <footer class="footer-bs">
            <div class="row a-color">
                <div class="col-md-3 footer-brand animated fadeInLeft">
                    <h2></h2>
                    <p><@spring.message code="footer.about.text" /></p>
                    <p>
                    <ol class="list-unstyled">
                        <li><a href="${rc.getContextPath()}/change?locale=ru_RU">Русский</a></li>
                        <li><a href="${rc.getContextPath()}/change?locale=en_EN">English</a></li>
                    </ol>
                    </p>
                </div>
                <div class="col-md-3 footer-nav animated fadeInUp">
                    <h4><@spring.message code="footer.helpful.links" /></h4>

                    <div>
                        <ul class="list">
                            <li><a href="#"><@spring.message code="footer.about.us" /></a></li>
                            <li><a href="#"><@spring.message code="footer.contacts" /></a></li>
                            <li><a href="#"><@spring.message code="footer.terms" /></a></li>
                            <li><a href="#"><@spring.message code="footer.privacy.policy" /></a></li>
                        </ul>
                    </div>
                </div>
                <div class="col-md-3 footer-social animated fadeInDown">
                    <h4><@spring.message code="footer.link.us" /></h4>
                    <ul>
                        <li><a href="#">Facebook</a></li>
                        <li><a href="#">Twitter</a></li>
                        <li><a href="#">Instagram</a></li>
                        <li><a href="#">Vkontakte</a></li>
                    </ul>
                </div>
                <div class="col-md-3 footer-ns animated fadeInRight">
                    <h4><@spring.message code="footer.news" /></h4>
                    <p><@spring.message code="footer.keep.news" /></p>
                    <p>
                    <div class="input-group">
                        <input type="email" class="form-control" placeholder="example@mail.com">
                        <span class="input-group-btn">
                            <button class="btn btn-default"
                                    type="button"><@spring.message code="footer.subscribe" /></button>
                        </span>
                    </div>
                    </p>
                </div>
            </div>
        </footer>
        <section style="text-align:center; margin:10px auto;">
            <p>© 2020 CachePlayer <a href="https://github.com/mipoks">by mipoks</a></p>
        </section>

    </div>
</div>
</body>
</html>