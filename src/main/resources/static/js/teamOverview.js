// Define contextpath
var ctx = $('#contextPathHolder').attr('data-contextPath');

// Get csrf token (needed to post a json through spring boot security)
$.ajaxSetup({
    beforeSend: function(xhr) {
        xhr.setRequestHeader('X-CSRF-TOKEN', $('#csrfToken').attr('data-csrfToken'));
    }
});

function quitAdmin(teamMembershipId, memberName, teamId, isQuitAdminConfirmed) {
    teammembership = {
        type: "TeamMembership",
        membershipId: teamMembershipId,
        team: {
            type: "Team",
            id: teamId,
        },
        member: {
            type: "Member",
            name: memberName,
        }
    }

    // Clear errorModal
    $('#errorModalHeader').empty();
    $('#errorModalBody').empty();
    $('#errorModal').modal('hide');

    if (isQuitAdminConfirmed) {
        $.ajax({
            url: ctx + "/team/quitadmin",
            method: "POST",
            contentType: "application/json; charset=UTF-8",
            data: JSON.stringify(teammembership),
            dataType: 'json',
            async: true,
            success: function(result) {
                    $('#teamDetailsButton').remove();
                    $('#quitAdminButton').remove();
                    $('#deleteTeamButton').remove();
                    $('#star-' + teamMembershipId).remove();
            },
            error: function(e) {
                alert("quitAdmin() error")
                console.log("ERROR: ",  e);
            }
        });
    } else {
        var isQuitAdminConfirmed = true;
        $('#errorModalHeader').append('<h4 class="modal-title">Groepsbeheerderschap opzeggen ?</h4><button type="button" class="close" data-dismiss="modal">&times;</button>');
        $('#errorModalBody').append('<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuleer</button>   ');
        $('#errorModalBody').append('<button type="button" class="btn btn-success" data-dismiss="modal" onclick="quitAdmin(' +
            teamMembershipId + ', \'' + memberName + '\', ' + teamId + ', ' + isQuitAdminConfirmed + ')">Stop groepsbeheerderschap</button>');
        $('#errorModal').modal('show');
    }
}

function quitTeam(teamMembershipId, memberName, teamId, isQuitTeamConfirmed, deleteTeamIfEmpty) {
        teammembership = {
            type: "TeamMembership",
            membershipId: teamMembershipId,
            team: {
                type: "Team",
                id: teamId,
            },
            member: {
                type: "Member",
                name: memberName,
            }
        }

        // Clear errorModal
        $('#errorModalHeader').empty();
        $('#errorModalBody').empty();
        $('#errorModal').modal('hide');

        if (isQuitTeamConfirmed) {
                $.ajax({
                    url: ctx + "/team/quit/" + deleteTeamIfEmpty,
                    method: "POST",
                    contentType: "application/json; charset=UTF-8",
                    data: JSON.stringify(teammembership),
                    dataType: 'json',
                    async: true,
                    success: function(result) {
                        if (deleteTeamIfEmpty) {
                            $('#card-' + teamId).remove();
                        } else {
                            setTimeout(function(){$('#errorModal').modal('show')}, 350);
                            $('#errorModalHeader').append('<h4 class="modal-title">Je bent het laatste groepslid:<br>Groep verwijderen ?</h4><button type="button" class="close" data-dismiss="modal">&times;</button>');
                            $('#errorModalBody').append('<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuleer</button>   ');
                            $('#errorModalBody').append('<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="quitTeam(' +
                                teamMembershipId + ', \'' + memberName + '\', ' + teamId + ', ' + isQuitTeamConfirmed + ', ' + true + ')">Verwijder groep</button>');
                        }
                    },
                    error: function(e) {
                        alert("quitTeam() error")
                        console.log("ERROR: ",  e);
                    }
                });
            } else {
                var isQuitTeamConfirmed = true;
                $('#errorModalHeader').append('<h4 class="modal-title">Wil je de groep echt verlaten ?</h4><button type="button" class="close" data-dismiss="modal">&times;</button>');
                $('#errorModalBody').append('<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuleer</button>   ');
                $('#errorModalBody').append('<button type="button" class="btn btn-success" data-dismiss="modal" onclick="quitTeam(' +
                    teamMembershipId + ', \'' + memberName + '\', ' + teamId + ', ' + isQuitTeamConfirmed + ', ' + deleteTeamIfEmpty +')">Verlaat groep</button>');
                $('#errorModal').modal('show');
            }
}

function deleteTeam(teamId, isDeleteTeamConfirmed) {
        teamToDelete = {
            type: "Team",
            id: teamId,
        }

        // Clear errorModal
        $('#errorModalHeader').empty();
        $('#errorModalBody').empty();
        $('#errorModal').modal('hide');

        if (isDeleteTeamConfirmed) {
                $.ajax({
                    url: ctx + "/team/delete",
                    method: "POST",
                    contentType: "application/json; charset=UTF-8",
                    data: JSON.stringify(teamToDelete),
                    dataType: 'json',
                    async: true,
                    success: function(result) {
                        $('#card-' + teamId).remove();
                    },
                    error: function(e) {
                        alert("deleteTeam() error")
                        console.log("ERROR: ",  e);
                    }
                });
            } else {
                var isDeleteTeamConfirmed = true;
                $('#errorModalHeader').append('<h4 class="modal-title">Wil je de groep echt verwijderen ?</h4><button type="button" class="close" data-dismiss="modal">&times;</button>');
                $('#errorModalBody').append('<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuleer</button>   ');
                $('#errorModalBody').append('<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteTeam(' +
                    teamId + ', ' + isDeleteTeamConfirmed + ')">Verwijder groep</button>');
                $('#errorModal').modal('show');
            }
}