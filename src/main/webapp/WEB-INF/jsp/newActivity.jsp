<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Activity</title>
</head>
<body>
<h1>Voer de activiteit in!</h1>
<br>

<form action="/activity/save" modelAttribute="activity">
    <input type="text" name="activityName" value="${updateActivityName}"><br><br>
        <select name="activityCategory">
            <option value="Huishouden" name="activityCategory">Huishouden</option>
            <option value="Medisch">Medisch</option>
            <option value="Vrije tijd" >Vrije tijd</option>
        </select>
    <input type="submit" value="Bewaar activiteit!">
</form>

</body>
</html>