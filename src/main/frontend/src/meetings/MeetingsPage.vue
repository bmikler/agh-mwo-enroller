<template>
  <div>
    <new-meeting-form @added="addNewMeeting($event)"></new-meeting-form>

    <span v-if="meetings.length == 0">
               Brak zaplanowanych spotkań.
           </span>
    <h3 v-else>
      Zaplanowane zajęcia ({{ meetings.length }})
    </h3>

    <meetings-list :meetings="meetings"
                   :username="username"
                   @attend="addMeetingParticipant($event)"
                   @unattend="removeMeetingParticipant($event)"
                   @delete="deleteMeeting($event)"></meetings-list>
  </div>
</template>

<script>
    import NewMeetingForm from "./NewMeetingForm";
    import MeetingsList from "./MeetingsList";

    export default {
        components: {NewMeetingForm, MeetingsList},
        props: ['username'],
        data() {
            return {
                meetings: []
            };
        },
        methods: {
            addNewMeeting(meeting) {                
                this.$http.post('meetings', meeting)
                    .then(response => {
                        console.log(response);
                        this.reloadMeetings();
                    })
                    .catch(err => console.log("error:" + err));

            },
            addMeetingParticipant(meeting) {

                this.$http.post('meetings/' + meeting.id +'/participants?username=' + this.username)
                    .then((response) => {
                        console.log(response);
                        this.reloadMeetings();
                    })
                    .catch(err => console.log("error" + err))

            },
            removeMeetingParticipant(meeting) {
                this.$http.delete('meetings/' + meeting.id +'/participants?username=' + this.username)
                    .then((response) => {
                        console.log(response);
                        this.reloadMeetings();
                    })
                    .catch(err => console.log("error" + err))
            },
            deleteMeeting(meeting) {

                this.$http.delete('meetings/' + meeting.id)
                    .then((response) => {
                        console.log(response);
                        this.reloadMeetings();
                    })
                    .catch(err => console.log("error" + err))
            },
            reloadMeetings() {

                this.$http.get('meetings')
                    .then(response => {
                        console.log(response.body);
                        this.meetings = response.body;
                    })

            }
        },

        mounted() {
            this.reloadMeetings();
        }
    }
</script>
