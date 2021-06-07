# Dash Tracker App

This is Dash Tracker, an app that lets you record DoorDash orders.
Once recorded, you can reverse search to see which restaurants give you the most earnings, which hours hold the most orders, and more.
This project is based off of my Java unit project in Base Camp Coding Academy, which had the same features built into a terminal.
I wanted to make the project into an app that I can use on the go.

## Usage

When you open the app, you will be greeted with the main menu.
From there, you can either record, show, search, or delete orders.
If you have not recorded an order previously, you can only record an order.

When recording an order, you have 8 feilds to fill in.
- Restaurant Name
- Pay
- Time of Order
- Day of Week
- Order Complete Time
- Miles Traveled
- Food Prep Performance
- Rating

Most Feilds have certain requirements in order to pass as valid.
Here are the requirements.
- Restaurant Name (No requirements)
- Pay (Needs to be a number, whether whole or decimal)
- Time of Order ([hour]:[minute] or [hour]:[minute]:[seconds] example "12:30" or "12:30:15")
- Day of Week (Needs atleast enough characters to be a distinct weekday, example "m", "tu", "sa", "Thursday")
- Order Complete Time ([minute], [hour]:[minute] or [hour]:[minute]:[seconds] example "30", "12:30" or "12:30:15")
- Miles Traveled (Needs to be a number, whether whole or decimal)
- Food Prep Performance (Either "Early", "On Pickup", "Late", or first letter of those)
- Rating (Not a text input)

Once you submit the order, you can display the order or delete it.

To view statistics on your orders, you can click on the search menu.
Once there, you can click on any of the buttons to view the coresponding data.
