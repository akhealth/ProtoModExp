<!DOCTYPE html>
<html lang="en">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
<title>ARIES</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="/uswds/dist/js/uswds-init.min.js"></script>
    <link rel="stylesheet" href="/uswds/dist/css/uswds.min.css" />
	<link href="/css/module.css" rel="stylesheet" />

	
</head>
<div class="usa-overlay"></div>
  <header class="usa-header usa-header--basic">
    <div class="usa-nav-container">
        <div class="usa-logo">
			<div class="header-image">
				<a href="/landingpage">
					<img src="uswds\dist\img\usa-icons\grid_view.svg" alt="ARIES">
				</a>
			</div>
        </div>
		<nav class="usa-breadcrumb" aria-label="Breadcrumbs,,">
			<ol class="usa-breadcrumb__list">
				<li class="usa-breadcrumb__list-item">
					<a href="/landingpage" class="usa-breadcrumb__link"><span>ARIES</span></a>
				</li>
				<li class="usa-breadcrumb__list-item usa-current" aria-current="page">
					<span>${moduleName}</span>
				</li>
			</ol>
		</nav>
      <nav aria-label="Primary navigation," class="usa-nav">
        <section aria-label="Search component">
          <form class="usa-search usa-search--small" role="search">
            <label class="usa-sr-only" for="search-field">Search</label>
            <input
              class="usa-input"
              id="search-field"
              type="search"
              name="search"
            />
            <button class="usa-button" type="submit">
              <img
                src="/uswds/dist/img/usa-icons-bg/search--white.svg"
                class="usa-search__submit-icon"
                alt="Search"
              />
            </button>
          </form>
        </section>
        <label for="logout" class="margin-x-1">Developer</label>
     	<a href="/login"><button type="button" name="logout" class="usa-button">
     		Logout</button></a>
      </nav>
    </div>
	</header>
	<main id="main-content">
		<div class="bg-base-lightest">
			<div class="modulepage">
			    <section class="grid-container usa-section">
				    <div class="module-section" id="module-color">
				      <div class="grid-container">
				        <p class="font-heading-lg">${moduleName}</p>
				      </div>
					</div>
			    </section>
			    <section class="grid-container usa-section">
					<div class="grid-row grid-gap-1">
						<div class="grid-col-6">
							<div class="module-section">
								<div class="grid-container">
									<p class="font-heading-lg">Quick Actions</p>
								</div>
								<div class="grid-row grid-gap">
							        <div class="tablet:grid-col">
							        	<ul class="usa-list">
							        		<li><a class="usa-link" href="/clientsearch">Find a Client</a></li>
							        		<li><a class="usa-link" href="/applicationsearch">Find an Application</a></li>
											<li><a class="usa-link" href="/module">Update Address</a></li>
							        		<li><a class="usa-link" href="/module">Start a New Application</a></li>
							        		<li><a class="usa-link" href="/module">Check Benefits</a></li>
							        	</ul>
							        </div>
								</div>
							</div>
						</div>
						<div class="grid-col-6">
							<div class="module-section">
								<div class="grid-container">
							        <p class="font-heading-lg">People</p>
								</div>
								<div class="grid-row grid-gap">
							        <div class="tablet:grid-col">
							        	<ul class="usa-list">
							        		<li>--------------------------------</li>
							        		<li>-------------------------</li>
							        		<li>------------------------------------------</li>
							        		<li>-------------------</li>
							        		<li>--------------------------------------------------------------</li>
							        	</ul>
							        </div>
								</div>
							</div>
						</div>
					</div>
			    </section>
			</div>
		</div>
  </main>
<script>
	const colorMap = new Map([
		  ['Worker Portal', " #73b3e7"],
		  ['Unified Search', "#d83933"],
		  ['Auto Renew Dashboard', '#00bde3'],
		  ['Account Management', '#fa9441']
		]);
	
	var moduleColor = colorMap.get('${moduleName}');

	document.getElementById('module-color').style.backgroundColor = moduleColor;
</script>
</html> 