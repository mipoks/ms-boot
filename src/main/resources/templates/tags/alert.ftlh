<#import "/spring.ftl" as spring/>
<div class="alert alert-<#if !info??>light<#else>${info.getColor()}</#if>" role="alert">
    <h4 class="alert-heading"><#if !info??><@spring.message code="alert.sponsor" /><#else>${info.getHead()}</#if></h4>
    <p><#if !info??><@spring.message code="alert.text" /><#else>${info.getBody()}</#if>
    </p>
    <hr>
    <p class="mb-0"><@spring.message code="alert.requisites" /></p>
</div>