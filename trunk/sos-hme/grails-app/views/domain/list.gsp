<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="basic" />
    <title>Domain list</title>
  </head>
  <body>
    <%--
    <div class="nav">
      <g:link action="unloadAll">Unload all</g:link>
    </div>
    --%>
    <div class="bodydomainlist">
      <div id="listadominios" class="listadominios">
        ${message(code:'domain.list.label.listadoDominios')}
      </div>
      <div id="logoucvhor" class="logoucvhor"></div>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="list">
        <g:each in="${folders}" status="i" var="folder">
          <div class="domainfolder">
            <g:link action="selectDomain" params="[path: folder.path]">
              <img src="${createLinkTo(dir: 'images', file: 'folder.png')}" /><br/>
              ${folder.name.value}
            </g:link>
          </div>
        </g:each>
      </div>
    </div>
  </body>
</html>
