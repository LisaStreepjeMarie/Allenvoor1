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
        $('#errorModalHeader').append('<h4 class="modal-title">Bevestig keuze</h4><button type="button" class="close" data-dismiss="modal">&times;</button>');
        $('#errorModalBody').append('<button type="button" class="btn btn-secondary" data-dismiss="modal">Annuleer opzeggen groepsbeheerder</button>   ');
        $('#errorModalBody').append('<button type="button" class="btn btn-success" data-dismiss="modal" onClick="quitAdmin(' +
            teamMembershipId + ', \'' + memberName + '\', ' + teamId + ', ' + isQuitAdminConfirmed + ')">Zeg groepsbeheerderschap op</button>');
        $('#errorModal').modal('show');
    }
}