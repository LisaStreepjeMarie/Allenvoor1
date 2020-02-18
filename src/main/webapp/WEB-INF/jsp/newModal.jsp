<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
    Launch demo modal
</button>
<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.3/angular.min.js"></script>
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js"></script>


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="background-color: coral;">



<ul class="nav nav-tabs" id="tabContent">
    <li class="active"><a href="#details" style="background-color: coral;" data-toggle="tab">Vrije tijd</a></li>
    <li><a href="#access-security" data-toggle="tab">Medisch</a></li>
</ul>

<div class="tab-content">
    <div class="tab-pane active" style="background-color: coral;" id="details">

        Details tab

        <div class="control-group">
            <label class="control-label">Instance Name</label>
        </div>
    </div>

    <div class="tab-pane" id="access-security">
        content 0
    </div>

</div>
        </div>
        <div class="modal-footer" style="background-color: coral;">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary">Save changes</button>
        </div>
    </div>
</div>
</div>

</body>
</html>