$(document).ready(function() {
        var ctx = $('#contextPathHolder').attr('data-contextPath');

        hideAllModalInputFields();

        <!-- this shows/hides the eventDone input field when the checkbox is toggled -->
        $("#eventDone").change(function () {
            if(document.getElementById("eventDone").checked == true) {
                    $("#datetimepickerDone").show()
            } else {
                    document.getElementById("eventDoneDate").removeAttribute("required");
                    $("#datetimepickerDone").hide()
            }
        });

        <!-- below makes sure that the unwanted fields in the modal are hidden and calls the selection upon change -->
        $("#activityCategory").change(function () {
            hideAllModalInputFields();
            activitySelection();
        });

        <!-- below cleans the modal upon closing -->
        $('#modal-form').on("hide.bs.modal", function() {
            $('#modal-form').trigger("reset");
            hideAllModalInputFields();
        });

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

                <!-- placeholder positie voor getMedication functie, moet in de modal gevuld worden denk ik -->
                getMedication();

                $(".fc-highlight").css("background", "purple");

                $('#save-change-event').attr('onclick', "saveEvent()");

                $('.modal').find('#eventStartDate').val(start.format('DD-MM-YYYY H:mm'));
                $('.modal').find('#eventEndDate').val(end.format('DD-MM-YYYY H:mm'));

                document.getElementById("modal-title").innerHTML = "Maak nieuwe afspraak";
                document.getElementById("save-change-event").innerHTML = "Maak afspraak";
                $("#delete-event").hide();
                $('.modal').modal('show');
            },

            <!-- This function is executed when an already planned event is clicked -->
            eventClick: function(event, element) {
                <!-- pass eventId to a simple <a href> link: -->
                <!-- $('#deleteEvent').attr('href', ctx + "/event/delete/" + event.id); -->

                <!-- redefines the onclick action of the delete-event button, so that it will point the browser -->
                <!-- to the /event/delete/{eventId}/{activityId} mapping -->
                $('#delete-event').attr('onclick',"window.location='" + ctx + "/event/delete/" + event.id + "/" + event.activity.id + "'");

                $("#eventId").val(event.id);
                $('#modal-form').attr('action', ctx + "/event/change/" + event.activity.id);
                $('#save-change-event').attr('action', ctx + "/event/change");
                $('.modal').find('#eventComment').val(event.description);
                $('.modal').find('#activityCategory').val(event.activity.category);
                $('.modal').find('#eventName').val(event.title);

                <!-- below shows the modal based on the event.activity.category -->
                fillingTheModal();
                showMedicationAmount(event, element);

                $('.modal').find('#eventStartDate').val(event.start.format('DD-MM-YYYY H:mm'));
                $('.modal').find('#eventEndDate').val(event.end.format('DD-MM-YYYY H:mm'));

                <!-- redefines the modal (popup) buttons with the appropriate button text -->
                document.getElementById("modal-title").innerHTML = "Wijzig of verwijder afspraak";
                document.getElementById("save-change-event").innerHTML = "Wijzig afspraak";

                <!-- shows the delete button on the (still hidden) modal -->
                $("#delete-event").show();

                <!-- lastly, the modal (popup) is shown, which by now has been properly configured -->
                $('.modal').modal('show');
            },

            eventDragStop: function(event, info) {
                console.log(event.title + " was dragged to start: " + event.start.format() + " and end: " + event.end.format());
            },

            eventResize: function(event, delta) {
                console.log(event.title + " was resized to start: " + event.start.format() + " and end: " + event.end.format());
            },

            // Distinct event colors based on activity.category
            eventRender: function(event, element) {
                if (typeof event.activity !== 'undefined') {
                    if( event.activity.category == "Medisch") {
                        element.css('background-color', '#000');
                    }
                }
            },

            <!-- Remember last view on page reload. -->
            viewRender: function (view, element) {
                localStorage.setItem("fcDefaultView", view.name);
            },
            defaultView: (localStorage.getItem("fcDefaultView") !== null ? localStorage.getItem("fcDefaultView") : "month"),

            editable: true,

            <!-- calendarData is a JSON string with all calendar events -->
            events: function(start, end, timezone, callback) {
                $.ajax({
                    type:'GET',
                    url: ctx + '/calendar/json/' + $('#teamId').attr('data-teamId') + '/' + start + '/' + end + '/',
                    dataType: 'json',
                    error: function (xhr, type, exception) { alert("Error: " + exception); },
                    success: function (response) {
                        var events = [];
                        for (i in response) {
                            events.push({
                                id: response[i].id,
                                title: response[i].title,
                                start: response[i].start,
                                end: response[i].end,
                                description: response[i].description,
                                activity: response[i].activity,
                            });
                        }
                        callback(events);
                    }
                });
             },

            eventLimit: true // allow "more" link when too many events
        });

        <!-- These functions load the start, end & done date calendars (datetimepickers) in the modal (popup). -->
        $('#datetimepickerStart').datetimepicker({
            format: 'DD-MM-YYYY HH:mm'
        });
        $('#datetimepickerEnd').datetimepicker({
            format: 'DD-MM-YYYY HH:mm'
        });
        $('#datetimepickerDone').datetimepicker({
            format: 'DD-MM-YYYY HH:mm'
        });
        $("#datetimepickerStart").on("change.datetimepicker", function (e) {
            $('#datetimepickerEnd').datetimepicker('minDate', e.date);
        });
        $("#datetimepickerEnd").on("change.datetimepicker", function (e) {
            $('#datetimepickerStart').datetimepicker('maxDate', e.date);
        });
        $('#datetimepickerDone').datetimepicker();

    });

<!-- below function shows the correct modal form based on the activity selection -->
    function activitySelection() {
        if ($("#activityCategory").val() === "Medisch") {
            $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
        } else {
            $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv").show();
        }
        $("#datetimepickerDone").hide();
        $("#eventDoneDiv").css("display", "");
    }

<!-- below function hides all modal options -->
    function hideAllModalInputFields() {
        $("#eventNameDiv, #eventCommentDiv, #medicationChoiceDiv, #eventDatesDiv, #takenMedicationDiv").css("display", "none");
    }

    function showMedicationAmount(event, element){
        if ($('.modal').find('#activityCategory').val() == "Medisch")
          $('.modal').find('#takenMedication').val(event.activity.takenmedication);
    }

<!-- below function fills the modal with event info if it exist -->
    function fillingTheModal() {
        if ($('.modal').find('#activityCategory').val() == "Medisch") {
            $("#eventNameDiv, #eventDateStartEndDiv, #medicationChoiceDiv, #takenMedicationDiv, #eventDatesDiv").show();
        } else {
            $("#eventNameDiv, #eventDateStartEndDiv, #eventDatesDiv, #eventCommentDiv").show();
        }
        $("#datetimepickerDone").hide();
        $("#eventDoneDiv").css("display", "");
    }


    /* This function is not available from jsp if within $(document).ready(function() */
    function saveEvent() {
        $.ajaxSetup({
            beforeSend: function(xhr) {
                xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
            }
        });

        currentEvent = {
            title: document.getElementById("eventName").value,
            start: moment(document.getElementById("eventStartDate").value, "DD-MM-YYYY H:mm").toDate(),
            end: moment(document.getElementById("eventEndDate").value, "DD-MM-YYYY H:mm").toDate(),
            description: document.getElementById("eventComment").value,
            activity: {
                name: document.getElementById("eventName").value,
                category: document.getElementById("activityCategory").value,
            },
            team: {
                id: $('#teamId').attr('data-teamId'),
            }
        }

        $.ajax({
            url : "/allenvooreen/calendar/saveEventFromPost",
            method : "POST",
            contentType : "application/json; charset=UTF-8",
            data : JSON.stringify(currentEvent),
            dataType : 'json',
            async : true,
            success : function(result) {
                console.log(result);
            },
            error : function(e) {
                alert("Error sending new event with AJAX!")
                console.log(currentEvent)
                console.log("ERROR: ", e);
            }
        });
    }

<!-- functie om de lijst te vullen met specifieke medicaties, moet nog aangepast worden voor med.id ipv med naam -->
    function getMedication(){
            $.ajax({
                 type:'GET',
                 url: '/allenvooreen/calendar/json/medications',
                 dataType: 'json',
                 success : function(result) {
                         List = result
                         $('#selectie2').empty();
                         $('#selectie2').append('<option value="">Kies een medicatie</option>');
                              for (i in List ) {
                              $('#selectie2').append('<option value="' + List[i].medicationname + '">' + List[i].medicationname + '</option>');
                     }
                 },
                 error : function(e) {
                 alert("error Error ERROR!")
                 console.log("ERROR: ", e);
                  }
          });
    }
