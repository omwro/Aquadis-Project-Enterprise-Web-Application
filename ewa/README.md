# User Manual For Project EWA Aquadis GeoChat

## Installation
To start playing the game you need to download the program as an zipfile from the master. Once you've downloaded the file extract it in intelij.
After that you should run npm install, to install al needed packages. After thats set up you can run ng serve. This will take a few sec to get the programming running. When its done you will see a message that you can visit localhost:4200.
When you visit the localhost:4200, you can select which page you want to start, see under this line.

## Login page
The login page is the first page you see when opening the website. You can sign in with your via 2 ways. You can either use the combination of email with password or the combination of username with password. Both will work. After you have wrote down your log in credentials, you can press the blue "Login" button to get your information validated and redirected to the main page. If you do not have an account. You can register an account through clicking "Register" on the navigation bar or clicking on the "Register" below the sign in page after the sentence: "Don't have an account?".

## Register page
The register page lets you register an account so you will be able to play the game. To register, you have to provide your unique email and username to the form. Also you have to provide a **Strong** password which you have to confirm it in the input field below it. After you have wrote all the credentials, you can proceed to press the blue button called "Register". If your account is successfully registered, you get redirected to the main page and you instantly allowed to play the game.

## Home page
The homepage is the mainpage of the game. Here you can see al the messages that are placed in the world, but you cant click them. To be able to click on a message you need to be in a radius of 100m. After you've clicked on a message you will receive 20points. Its not possible to keep getting 20 points for a message you just opened. You need to open new messages to get new points. 
Its also possible to drop new messages in the homepage. For this you can just simply click on the message icon at the rightbottom. You can fill in a title and message to bee seen by other players.

## Profile page
### Open the profile page
The profile page should be accessible after being logged in. You can open the profile page through clicking on "My profile" inside the navigation bar on top of the screen.

### Your profile information
Your profile page has 2 diffent pages. The first profile page is the page about your profile information just like: your nickname, your status and your score. 

The second profile page is the page about your account information just like: your email, your username and your encrypted password.

Both pages are accessible though the small button **below** the "Edit profile". The "Advanced" button sends you to the second profile page about your account information. The "Simple" button sends you to the first profile page about your profile.

### Edit your profile
If you would like to edit your profile page, then you press the blue button called "Edit Profile". 

If you are on the *Simple* page, you are allowed to change your Avatar by selecting an avatar inside the dropdownlist. The avatar above will change immediately after selecting one in the dropdownlist. You can also change your nickname through writing a new nickname. Last but not least, you can set your profile status to private by checking the checkbox.

If you are on the *Advanced* page, you are allowed to change your email or username by writing a new email-address or username. A requirement is that both of them should be unique not be used by someone else. You can also change your password. Keep in mind that you have to confirm your password by writing it again and they have to match to be able to save your new password.

If you are done editing, you can save the changes by clicking on the save button called "Save". If it see red error messages pop up, than you should read them carefully and correct your mistakes. If it doesn't give red error messages and you get send to the main profile page, then the changes are succesfully saved and also changed on the page. And you don't want to save the changes, you can press the red button called "Discard". Pressing this button will send you to the main profile page without saving anything.

## Leaderboard
The leaderboard will show you the top 5 players with the most users. This page shows the rank, avatar, nickname and score of the user. It will only show the users if the profiles are public and not private. If the profile is private, his/her place will be replaced with the next one. The leaderboard is accessible by everyone, even if you are not logged in.

## Admin
There is also an admin portal where you can see how many messages there are, and also what the messages contains. This so the Admin can disabled inappropriate messages. The admin can also see the active users who are using the game. It is also possible for the admin to disable/enalbe users.

This everything is possible within one page for the ease of use. To use the admin portal simply visit localhost:4200/admin and sign in with the given admin credentials.


## Testing
### Front-end
To run all the front end tests, you first have to open the project in an IDE. For this tutorial, we are using Intellij Idea as our main IDE. In case you are using another IDE,
please head over the user manual of the IDE of your liking.

After opening the project, you can open up a new terminal state and enter the command `ng test`. After some seconds the test will run and also display a new browser pop-up
which will automatically open the url `localhost:9876`. You can also identify it with the big header called 'Karma'.  It will tell you how many test(specs) files has been tested
and also how many have failed. The green dots/texts mean that the specific test is working and the red dots/cros(X)/texts means that the specific tests has been failed.
All the test files are inside the spec files of the original component folder/directory. An example of a test file name is "component.spec.ts".

### Back-end
To run all the front end tests, you first have to open the project in an IDE. For this tutorial, we are using Intellij Idea as our main IDE. In case you are using another IDE,
please head over the user manual of the IDE of your liking.

After opening the project, you can look inside the project directory tree and head over to "api/src/test/java/hva.nl/api" to find all the test files created. You can choose
file inside this directory and run the test. The tests will open a new Run state and tell you if the test have succeeded or failed bases on the status your get at the end of
running the test.


