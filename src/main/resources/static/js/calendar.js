$(document).ready(function() {
        <!-- loads the calendar (without data) -->
        $('#calendar').fullCalendar({
            themeSystem: 'bootstrap4',
            timeZone: 'Europe/Amsterdam',
            timeFormat: 'H(:mm)',
            locale: 'nl',

            <!-- The buttons and title that are displayed at the top of the calendar -->
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay,list'
            },

            weekNumbers: true,
            eventLimit: true, // allow "more" link when too many events
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectHelper: true,

            <!-- This function is executed when an empty date/time is clicked -->
            select: function(start, end) {
                $('#modal-form').attr('action',"${pageContext.request.contextPath}/event/new");
                $('#save-change-event').attr('action',"${pageContext.request.contextPath}/event/new");
                $('.modal').find('#eventStartDate').val(start);
                $('.modal').find('#eventEndDate').val(end);

                document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
                document.getElementById("save-change-event").innerHTML = "Maak afspraak";
                $("#delete-event").hide();
                $('.modal').modal('show');
            },

            <!-- This function is executed when an already planned event is clicked -->
            eventClick: function(event, element) {
                <!-- pass eventId to a simple <a href> link: -->
                <!-- $('#deleteEvent').attr('href',"${pageContext.request.contextPath}/event/delete/" + event.id); -->

                <!-- redefines the onclick action of the delete-event button, so that it will point the browser -->
                <!-- to the /event/delete/{eventId}/{activityId} mapping -->
                $('#delete-event').attr('onclick',"window.location='${pageContext.request.contextPath}/event/delete/" + event.id + "/" + event.activity.id + "'");

                $("#eventId").val(event.id);
                $('#modal-form').attr('action',"${pageContext.request.contextPath}/event/change/" + event.activity.id);
                $('#save-change-event').attr('action',"{pageContext.request.contextPath}/event/change");
                $('.modal').find('#eventComment').val(event.description);
                $('.modal').find('#selectie').val(event.activity.category);
                $('.modal').find('#eventName').val(event.title);

                <!-- below shows the modal based on the event.activity.category -->
                fillingTheModal();
                showMedicationAmount(event, element);

                $('.modal').find('#eventStartDate').val(event.start);
                $('.modal').find('#eventEndDate').val(event.end);

                <!-- redefines the modal (popup) buttons with the appropriate button text -->
                document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
                document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";

                <!-- shows the delete button on the (still hidden) modal -->
                $("#delete-event").show();

                <!-- lastly, the modal (popup) is shown, which by now has been properly configured -->
                $('.modal').modal('show');
            },

            eventDragStop: function(info) {
            },

            <!-- This function is executed when an event was resized, not yet implemented -->
            eventResize: function(event, delta, revertFunc) {
                alert(event.title + " end is now " + event.end.format());

                revertFunc();
            },

            <!-- Remember last view on page reload. -->
            viewRender: function (view, element) {
                localStorage.setItem("fcDefaultView", view.name);
            },
            defaultView: (localStorage.getItem("fcDefaultView") !== null ? localStorage.getItem("fcDefaultView") : "month"),

            editable: true,

            <!-- calendarData is a JSON string with all calendar events -->
            eventLimit: true // allow "more" link when too many events
        });


        <!-- this shows/hides the eventDone input field when the checkbox is toggled -->
        $("#eventDone").change(function () {
            if(document.getElementById("eventDone").checked == true) {
                    $("#eventDoneDateDiv").show()
                    $('#eventDoneDate').attr('required', "true")
                    $('#eventDoneDate').attr('name',"eventDoneDate")
            } else {
                    document.getElementById("eventDoneDate").removeAttribute("required");
                    $("#eventDoneDateDiv").hide()

                    <!-- set name of eventDoneDate to noEventDoneDate -->
                    <!-- so the controller doesn't pick up the value (and will not write an empty value into java.util.Date) -->
                    $('#eventDoneDate').attr('name',"noEventDoneDate");
            }
        });

        <!-- Makes unwanted fields in the modal hidden and calls the selection upon change -->
        $("#selectie").change(function () {
            hideAll();
            activitySelection();
        });

        <!-- Cleans the modal upon closing -->
        $('#modal-form').on("hide.bs.modal", function() {
            $('#modal-form').trigger("reset");
            hideAll();
        });

        <!-- Shows correct modal form based on the activity selection -->
            function activitySelection() {
                if ($("#selectie").val() === "Medisch") {
                    $("#ShowDates, #ShowEventName, #medicationActivity").show();
                } else {
                    $("#ShowDates, #ShowEventName, #eventActivity").show();
                }
                $("#eventDoneDiv").css("display", "");
            };

        <!-- Hides all modal options -->
        function hideAll() {
            $("#ShowDates, #eventActivity, #medicationActivity, #ShowEventName, #eventDoneDateDiv, #eventDoneDiv").css("display", "none");
        }
        hideAll();

        function showMedicationAmount(event, element){
            if ($('.modal').find('#selectie').val() == "Medisch")
              $('.modal').find('#takenMedication').val(event.activity.takenmedication);
        }

        <!-- below function fills the modal with event info if it exist -->
        function fillingTheModal() {
            if ($('.modal').find('#selectie').val() == "Medisch")
                $("#ShowDates, #ShowEventName, #medicationActivity").show();
            else
                $("#ShowDates, #ShowEventName, #eventActivity").show();
        }
})