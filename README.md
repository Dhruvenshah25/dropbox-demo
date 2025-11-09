# Dropbox Business API Demo (Spring Boot)

This project demonstrates how to integrate Dropbox Business APIs using Spring Boot, focusing on retrieving team members data through the `/team/members/list` endpoint.

---

## Features

- OAuth 2.0 or manual team access token authentication
- Fetch Dropbox Business team members list
- Built using Spring Boot and RestTemplate
- Tested with a Dropbox Business Advanced account

---

## Tech Stack

- Java 17+
- Spring Boot 3.x
- Maven
- Dropbox Business API
- Postman (for testing)

---

## Setup Instructions

### 1. Create a Dropbox Business App

1. Go to the [Dropbox App Console](https://www.dropbox.com/developers/apps)
2. Click **Create app**
3. Choose:
    - Scoped access
    - Full Dropbox access
4. Set your redirect URI (if you later enable OAuth flow):
5. Enable required scopes:
- `team_info.read` 
- `members.read` 
- `event.read`
6. Generate an access token (team access) from the Admin Console or via OAuth.
7. Copy this token for use in the application.

---

### 2. Configure Your Project

### 3. Run the spring-boot project

### 4. Test the Api's

1.Call '/oauth/login' api's which will redirect to dropbox login page.
2.Sign in into dropbox account.
3.Upon successful sign-in you will be redirected to '/oauth/callback' with a token provided in response.
4.Call '/dropbox/team-members' api to fetch the team members.

