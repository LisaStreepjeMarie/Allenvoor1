<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link href="${pageContext.request.contextPath}/webjars/font-awesome/5.12.0/css/all.min.css" rel='stylesheet'>
<style>

.modal-footer {
    border: 0;
    }

    .tabs {

    position: fixed;
    width: 90px;
    height: 90px;

}

@media (min-width: 768px) {
  .tabs {
    top: calc(12px + 9%);
    right: calc(234.5px + 50%);
    left: auto;
  }

}

.modal-header {
    background: #4a4a4a;
    padding: 0p;
    height: 57px;
    border: 0;
}

.tabs-top {
    margin-bottom: 1px;
    margin-top: 1px;
}

.nav-tabs {
    border: 0;

}

.tabs-4 .nav-tabs > li {
    width: 200px;
    border: 0;

}

.nav-tabs > li > a {
    width: 230px;

    background: #4a4a4a;
    color: #fff;

    text-align: center;
}

.nav-tabs > li > a:hover,
.nav-tabs > li.active > a,
.nav-tabs > li.active > a:hover,
.nav-tabs > li.active > a:focus {
    border: 0;
    background: #4a4a4a;
    color: #ffd800;
}

.nav-tabs > li.active > a {
    border: 0;
    background: #4a4a4a;
    color: #fff;
}

.nav-tabs > li.active > a.active {
    border: 0;
    background: #4a4a4a;
    color: #ffd800;
}


.nav-item {
    vertical-align:top;
}
</style>
</head>
<body>
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
    Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade newModal" id="exampleModal" style="widh: 200px;" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <!-- tab options on top -->
            <div class="modal-header">
                <div class="tabbable">
                    <ul class="nav nav-tabs" role="tablist">
                        <li >
                            <a class="nav-link" onclick="optionLeisureActivity()" href="#leisureActivity" data-toggle="tab">
                                Vrije tijd
                            </a>
                        </li>
                        <li>
                            <a class="nav-link" href="#MedicationActivity" data-toggle="tab">
                                Medisch
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- model content divs -->
            <div class="row mt-4">
                <div class="col-8">
                    <div class="tab-content" id="nav-tabContent">
                        <!-- event titel starts here, same for leisureActivity and medicationActivity -->
                        <div class="modal-body" id="eventNameDiv">
                            <div class="row col-4">
                                <input type="text" placeholder="Titel"name="eventName" id="eventName" />
                                <input type="hidden" name="eventId" id="eventId" />
                                <input type="hidden" name="activityId" id="activityId" />
                                <input type="hidden" name="teamId" id="team.teamId"/>
                                <input type="hidden" name="teamName" value="${team.teamName}" id="teamName"/>
                            </div>
                        </div>

                        <!-- leisureActivity options start here -->
                        <div class="tab-pane fade" id="leisureActivity" role="tabpanel" >
                            <!-- TODO needs to be more then 1 line, maybe a text box -->
                            <div class="modal-body" id="eventCommentDiv">
                                <div class="row col-4">
                                    <input type="text" placeholder="Beschrijving" name="eventComment" id="eventComment" />
                                </div>
                            </div>
                        </div>

                        <!-- medicationActivity options start here -->
                        <div class="tab-pane fade" id="MedicationActivity" role="tabpanel" >
                            <div class="modal-body" id="medicationChoiceDiv">
                                <div class="row col-4">
                                    <select name="medication.medicationId" id="medicationChoice" >
                                        <option disabled selected="selected">Medicijn</option>
                                    </select>
                                </div>
                            </div>
                            <div class="modal-body" id="takenMedicationDiv">
                                <div class="row col-4">
                                    <input type="number" placeholder="aantal" name="takenMedication" id="takenMedication" />
                                </div>
                            </div>
                        </div>

                        <!-- date options start here, leisureActivity and medicationActivity -->
                        <!-- TODO -->

                        <!-- memberOptions start here -->
                        <div class="tab-pane fade" id="memberOptions" role="tabpanel" aria-labelledby="list-settings-list">
                            <div class="modal-body">

                                <div class="row col-4" id="doneByMemberDiv">
                                    <div class="w-25 p-3">

                                        <ul class="list-group" >
                                            <li class="list-group-item list-group-item-dark">Ingeschreven gebruikers:</li>
                                            <div id="subscriptionList">
                                                <!-- this list is filled by getEventSubscriptions() in the events.js -->
                                            </div>
                                        </ul>
                                    </div>
                                    <div class="w-25 p-3" id="subscribe">
                                        <!-- placeholder for subscribe-button; is filled by getEventSubscriptions() in the events.js -->
                                    </div>
                                    <select name="event.doneByMember" id="doneByMember" style="width:13.2em;" >
                                        <option disabled selected="doneByMember">Gedaan door</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>

        </div>
    </div>
    <!-- memberOption side tab -->
    <div class="nav nav-tabs col-4 tabs" role="tablist">
    <a class="list-group-item  list-group-item-action nav-link" style="background-color:#ccb3ff;" id="Memberlist" data-toggle="tab" href=#memberOptions role="tab" aria-controls="settings"><i class="fas fa-user float-center"></i></a>
    </div>
</div>
<script>
    $(document).ready(function(){
        eventId = document.getElementById("eventId").value;
        getEventSubscriptions(eventId);
    })
</script>
</body>
</html>