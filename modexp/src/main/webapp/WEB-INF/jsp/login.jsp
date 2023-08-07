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
    	<link href="/css/login.css" rel="stylesheet" />

</head>
<body>
  <div class="usa-overlay"></div>
  <header class="usa-header usa-header--basic">
    <div class="usa-nav-container">
        <div class="usa-logo">
			<div class="header-image">
				<a href="/landingpage">
					<img src="uswds\dist\img\usa-icons\grid_view.svg" alt="ARIES">
				</a>
			</div>
		<nav class="usa-breadcrumb" aria-label="Breadcrumbs,,">
			<ol class="usa-breadcrumb__list">
				<li class="usa-breadcrumb__list-item usa-current" aria-current="page">
					<span>ARIES</span>
				</li>
			</ol>
		</nav>
        </div>
    </div>
  </header>
  <main id="main-content">
    <div class="bg-base-lightest">
      <section class="grid-container usa-section">
        <div class="grid-row flex-justify-center">
          <div class="grid-col-12 tablet:grid-col-8 desktop:grid-col-6">
            <div
              class="
                bg-white
                padding-y-3 padding-x-5
                border border-base-lighter
              "
            >
              <h1 class="margin-bottom-0">Sign in</h1>
              <form class="usa-form" action='/login' method='POST'>
                <fieldset class="usa-fieldset">
                  <legend class="usa-legend usa-legend--large">
                    Access your account
                  </legend>
                  <label class="usa-label" for="email">Username</label>
                  <input
                  	class="usa-input"
                    id="username"
                    name="username"
                    value = '${param.username}'
                    type="text"
                    autocapitalize="off"
                    autocorrect="off"
                    
                  />
                  <label class="usa-label" for="password-sign-in"
                    >Password</label
                  >
                  <input
                  	class="usa-input"
                    id="password-sign-in"
                    name="password"
                    value = '${param.password}'
                    type="text"
                  />
                  <button
                    title="Toggle password"
                    type="button"
                    class="usa-show-password"
                    aria-controls="password-sign-in"
                    data-show-text="Show password"
                    data-hide-text="Hide password"
                  >
                    Show password
                  </button>
							<button type="submit" name ="submit" value="submit" 
							class="usa-button">Submit</button>
                  <p>
                    <a href="javascript:void()" title="Forgot password"
                      >Forgot password?</a
                    >
                  </p>
                </fieldset>
              </form>
            </div>
            <p class="text-center">
              Don't have an account?
              <a class="usa-link" href="javascript:void(0);"
                >Create your account now</a
              >.
            </p>
          </div>
        </div>
      </section>
    </div>
  </main>
</body>
</html> 