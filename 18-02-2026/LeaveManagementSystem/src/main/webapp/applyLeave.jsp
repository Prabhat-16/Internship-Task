<!DOCTYPE html>
<html>
<head>
    <title>Apply Leave</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4">
        <h3>Apply Leave</h3>
        <hr>

        <form action="applyLeave" method="post">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label>From Date</label>
                    <input type="date" name="from" class="form-control" required>
                </div>

                <div class="col-md-6 mb-3">
                    <label>To Date</label>
                    <input type="date" name="to" class="form-control" required>
                </div>
            </div>

            <div class="mb-3">
                <label>Reason</label>
                <textarea name="reason" class="form-control" required></textarea>
            </div>

            <button class="btn btn-success">Submit Leave</button>
        </form>
    </div>
</div>

</body>
</html>
