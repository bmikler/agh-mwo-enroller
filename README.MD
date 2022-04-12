
## MWO Enroller

### [Heroku deployment](https://mwoenroller.herokuapp.com/)

## Endpoints:

### Basic:
 - get all meetings - GET /meetings
 - get single meeting by id - GET /meetings/{id} - request body new meeting.
 - add meeting - POST /meetings
 - add participant to the meeting - POST meetings/{id}/participants?login={participant login}
 - get all participants from meeting - GET /meetings/{id}/participants
### Gold:
 - delete meeting - DELETE /meetings/{id}
 - update meeting - PUT /meetings/{id} - request body new meeting.
 - remove participant from meeting - DELETE meetings/{id}/participants?login={participant login}
### Premium:
 - sort meetings by title - GET /meetings?sorted=true
 - search meetings by title or description contains text GET /meetings/search?text={search text}
 - search meetings by participant signed to them GET /meetings/search?participant={participant login}