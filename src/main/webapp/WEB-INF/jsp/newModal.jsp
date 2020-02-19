<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<style>


.btn {
  margin-top: 30px;
  display: block;
}

    .tabs {
    top: 20vh;
    left: 1.2em;
    position: fixed;
    width: 47px;
    height: 20px;

}

@media (min-width: 768px) {
  .tabs {
    top: calc(10px + 5%);
    right: calc(259px + 50%);
    left: auto;
  }
}
</style>
</head>
<body>
<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
    Launch demo modal
</button>

<!-- Modal -->
<div class="modal fade newModal" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">

            <div class="modal-body">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                    <!-- Nav tabs -->
                    <div class="row mt-4">

                        <div class="col-8">
                            <div class="tab-content" id="nav-tabContent">
                                <div class="tab-pane fade" id="LeasureActivity" role="tabpanel" aria-labelledby="list-messages-list">
                                    <div class="modal-body" id="eventNameDiv">
                                        <div class="row">
                                            <label class="col-4" for="eventName">Onderwerp</label>
                                            <input type="text" name="eventName" id="eventName" />
                                            <input type="hidden" name="eventId" id="eventId" />
                                            <input type="hidden" name="activityId" id="activityId" />
                                            <input type="hidden" name="teamId" id="team.teamId"/>
                                            <input type="hidden" name="teamName" value="${team.teamName}" id="teamName"/>
                                        </div>
                                    </div>

                                    <!-- event with activity modal input fields -->
                                    <div class="modal-body" id="eventCommentDiv">
                                        <div class="row">
                                            <label class="col-4" for="eventComment">Beschrijving</label>
                                            <input type="text" name="eventComment" id="eventComment" />
                                        </div>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="MedicationActivity" role="tabpanel" aria-labelledby="list-settings-list">
                                    <div class="modal-body" id="medicationChoiceDiv">
                                        <div class="row">
                                            <label class="col-4" for="medicationChoice" control-label>Medicijn</label>
                                            <select name="medication.medicationId" id="medicationChoice" >
                                                <option disabled selected="selected">Kies een medicijn</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-body" id="takenMedicationDiv">
                                        <div class="row">
                                            <label class="col-4" for="takenMedication" control-label>Hoeveelheid</label>
                                            <input type="number" name="takenMedication" id="takenMedication" />
                                        </div>
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
    <div class="col-4 tabs">
        <div class="list-group " id="list-tab" role="tablist">
            <a class="list-group-item  list-group-item-action" id="list-messages-list" data-toggle="list" href="#LeasureActivity" role="tab" aria-controls="messages">Vrije tijd</a>
            <a class="list-group-item  list-group-item-action" id="list-settings-list" data-toggle="list" href=#MedicationActivity role="tab" aria-controls="settings">Medisch</a>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.5/popper.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script>
        function equalHeight(group) {
            tallest = 0;
            group.each(function() {
                thisHeight = $(this).height();
                if (thisHeight > tallest) {
                    tallest = thisHeight;
                }
            });
            group.height(tallest);
        }

        $(document).ready(function() {
            equalHeight($(".equalHeight"));
        });
        //# sourceURL=pen.js
    </script>
</body>
</html>