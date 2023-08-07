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
	<link href="/css/clientdetails.css" rel="stylesheet" />

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
				<li class="usa-breadcrumb__list-item">
					<a href="/clientsearch" class="usa-breadcrumb__link"><span>Client Search</span></a>
				</li>
				<li class="usa-breadcrumb__list-item usa-current" aria-current="page">
					<span>Client Details</span>
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
		<section class="grid-container usa-section">
			<form action='/clientdetails' method='POST'>
				<div class="grid-container">
		        	<h2 class="font-heading-xl margin-y-4">Client ID: ${clientId}</h2>
				</div>
				<div class="border-top border-base-lighter margin-top-3 padding-top-1">
			      <div class="grid-row grid-gap">
			        <div class="tablet:grid-col-4">
				        <p class="clientdetails"><strong>First Name:</strong></p>
				        <p class="clientdetails">${firstName}</p>
			        </div>
			        <div class="tablet:grid-col-4">
				        <p class="clientdetails"><strong>Last Name:</strong></p>
				        <p class="clientdetails">${lastName}</p>
			      	</div>
			        <div class="tablet:grid-col-1 grid-offset-3">
						<button class='usa-button margin-top-1' 
						name='edit' value="edit" disabled>Edit</button>
			      	</div>
			      </div>
				</div>
				<div class="border-top border-base-lighter margin-top-3 padding-top-1">
					<div class="grid-row grid-gap">
						<div class="tablet:grid-col-4">
							<fieldset class="usa-fieldset">
								<label class="usa-label"><strong>Address:</strong></label>
								<p class="clientdetails">${firstName}</p>
								<label class="usa-label"><strong>Phone:</strong></label>
								<p class="clientdetails">${firstName}</p>
							</fieldset>
						</div>
				        <div class="tablet:grid-col-1 grid-offset-7">
							<button class='usa-button margin-top-1' 
							name='edit' value="edit" disabled>Edit</button>
				      	</div>
					</div>
				</div>
				<div class="border-top border-base-lighter margin-top-3 padding-top-1">
					<div class="grid-row grid-gap">
						<div class="grid-col-10">
							<div class="grid-row grid-gap">
								<p class="address"><strong>Applications: </strong></p>
								<p class="address"> ${firstName}</p>
							</div>							
						</div>
				        <div class="grid-col-1 grid-offset-1">
							<button class='usa-button margin-top-1'
							 name='edit' value="edit" disabled>Edit</button>
				      	</div>
					</div>
				</div>
			</form>
	    </section>
	 </div>
  </main>
</html> 