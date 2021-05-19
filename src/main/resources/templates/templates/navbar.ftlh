<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8"/>
</head>
<body>
<div fragment="navbar">
    <nav class="navbar navbar-expand-lg navbar-light nav-color">
        <a class="navbar-brand" href="${rc.getContextPath()}/search"><@spring.message code="navbar.main" /></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon my-menu-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${rc.getContextPath()}/me"><@spring.message code="navbar.my.music" /><span
                                class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${rc.getContextPath()}/actual"><@spring.message code="navbar.recommendation" /></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="${rc.getContextPath()}/login">
                        <@spring.message code="navbar.hello" />
                        , <#if user??> ${user.getName()} <#else> <@spring.message code="navbar.listener" /> </#if>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${rc.getContextPath()}/chat"><@spring.message code="navbar.messenger" /></a>
                </li>
                <#if user??>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <@spring.message code="navbar.account" />
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item"
                               href="${rc.getContextPath()}/profile"><@spring.message code="navbar.settings" /></a>
                            <div class="dropdown-divider"></div>

                            <form id="logout-form" action="${rc.getContextPath()}/logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit"
                                        class="dropdown-item"><@spring.message code="navbar.logout" /></button>
                            </form>

                        </div>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${rc.getContextPath()}/login"><@spring.message code="navbar.login" /></a>
                    </li>
                </#if>
            </ul>
            <form method="GET" action="${rc.getContextPath()}/search" class="form-inline my-2 my-lg-0">
                <input class="form-control form-control-2 mr-sm-2" name="search" type="search"
                       placeholder="<@spring.message code="navbar.listen" />"
                       aria-label="Search">
                <button class="btn btn-default my-2 my-sm-0"
                        type="submit"><@spring.message code="navbar.search" /></button>
            </form>
        </div>
    </nav>
</div>

</body>

</html>