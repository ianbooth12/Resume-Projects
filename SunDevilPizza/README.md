# This is a group academic project copied to my personal Projects repository for employer reference only.
## Author credits include myself and four other ASU students on GitHub: @kadinrabo , @SShar195 , @CamJam2048 , @KineticKoi

## SunDevilPizza.java

SunDevilPizza.java is the entrance to the application. It houses important properties (e.g. a scene, sessions, rootNodes, etc.) and generates a welcome UI, which is the next file.

## WelcomeUI.java

WelcomeUI.java creates an order and login button. Order button is for a customer, login button is for other users like the worker or admin. When the order button is pressed, the user is redirected to PizzaBuilderUI. When the login button is pressed, the user is redirected to an ASURITE LoginUI.

## PizzaBuilderUI.java

PizzaBuilderUI.java defines pizza toppings, pizza type, and many properties for the UI. The constructor configures the many UI properties, so it is quite long for that reason. There's a lot going on in this order customization page. Roughly, the constructor:

1. Sets up the lables for customization, pizza toppings, time selection, order summary, and others.
2. Configures a choice box for a time picker, and hooks up a handler for it. When a time is selected, it sets the pickup time for the user's current order and refreshes the order summary with the user's selections (e.g. pizza type, size, toppings, and pickup time)
3. Configures the scroll pane for selecting toppings
4. Configures a size selector and base pizza selector.
5. Configures an text field for order summary that is not editable. The application updates it.
6. Configures essential back and forward buttons.

If the user presses the forward button and there are selections made to size, type, and time, the user is redirected to the order summary UI.

## LoginUI.java

Declares properties for a proper login screen such as username and password fields, sign in buttons, customer hub, back button, and others. The constructor:

1. Configures the username and password field
2. Configures the header label to say "Login: "
3. Configures the sign in button to say "Sign in: ". It's hooked up to a LoginControlsHandler.
   When pressed, the user is determined to be a customer or employee and handled accordingly. Customer is redirected to the CustomerPortalUI. Employee is redirected to the EmployeePortalUI.
4. Configures a customer hub button that says "Profile". It's hooked up to a LoginControlsHandler. When pressed, it redirects the user to the CustomerPortalUI.
5. Configures a sign out button It's hooked up to a LoginControlsHandler. When pressed, it redirects the user to the landing page and logs the user out.
6. Configures a sign in hyperlink It's hooked up to a LoginControlsHandler. When pressed, it redirects the user to the sign in page.
7. Configures a login failed Label for failed logins.
8. Configures a back button, and checks if the user is already signed in.

## CustomerPortalUI.java

Declares labels and buttons for the UI and scroll panes for the current order and order history. Also includes the current order list and previous order list for keeping track. The constructor:

1. Configures all necessary labels
2. Configures the current orders scroll pane with all orders that are not currently "READY"
3. Configures the previous orders scroll pane with all previous orders (in the list).
4. Configures a home button, which when pressed, redirects the user to the landing page.

## EmployeePortalUI.java

Declares labels and buttons for the UI, a queue scroll pane, and button texts. The constructor:

1. Configures a home button to send the user to the landing page
2. Configures a queue scroll pane by adding orders to the queue that have been accepted by the order processing agent or ready to cook or cooking by the chef. All that information is added to the queue scroll pane.
3. Configures buttons and labels

## OrderSummaryUI.java

Declares buttons, labels, and text areas/fields as necessary for the UI. The constructor:

1. Configures the order summary text field with orderSummary information
2. Configures the ASURITE ID field to prompt user to enter their ASURITE
3. Configures the password field to prompt user to enter their password
4. Configures a verify button for checking ASURITE ID. If it is pressed, verify the ID is not a duplicate. If the ID doesn't exist, a new customer is created and prompted for a password.
5. Configures an email field for the user to enter their email (for updates on their order)
6. Configures a purchase button that when pressed, it checks if everything is populated properly and sets current order attributes for the user such as email, session, order number, and status. The customer is added to the file manager in the process.
7. Configures a back button that when pressed, it goes back to previous page

Once purchased, the user is redirected to the OrderConfirmationUI

## OrderConfirmationUI.java

Declares labels and confirmation button. The constructor:

1. Configures the header label and sub label with proper information
2. Configures the confirmation button and when pressed goes to the CustomerPortalUI
