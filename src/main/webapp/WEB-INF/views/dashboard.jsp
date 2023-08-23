<%@include file="fragments/header.jsp" %>


<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <section class="content-header">
        <h1>Dashboard <span> <i class="fa fa-angle-double-right"></i>
				Welcome
			</span>
            <a href="${baseUrl}/help"><span class="float-right help-section"><i class="material-icons">help</i></span>
            </a></h1>
    </section>
    <section class="content">
        <div class="box">
            <div class="box-header with-border">

                <div class="rounded-action-btn">
                    <a href="###" class="btn btn-danger" role="button"><i class="material-icons">delete_sweep</i></a>
                </div>
                <div class="rounded-action-btn">
                    <a href="###" class="btn btn-warning" role="button"><i class="material-icons">edit</i></a>
                </div>
                <div class="rounded-action-btn">
                    <a href="###" class="btn btn-primary" role="button"><i class="material-icons">add</i></a>
                </div>
            </div>
            <div class="box-body">

				<div class="row">
					<div class="col-lg-3 col-6">
						<!-- small box -->
						<div class="small-box bg-info">
							<div class="inner">
								<h3>${patientCount}</h3>

								<p>Total Patient</p>
							</div>
							<div class="icon">
								<i class="ion ion-bag"></i>
							</div>
							<a href="${baseUrl}/patient/" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-6">
						<!-- small box -->
						<div class="small-box bg-success">
							<div class="inner">
								<h3>${doctorCount}</h3>

								<p>Total Doctor</p>
							</div>
							<div class="icon">
								<i class="ion ion-stats-bars"></i>
							</div>
							<a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-6">
						<!-- small box -->
						<div class="small-box bg-warning">
							<div class="inner">
								<h3>${specialistCount}</h3>

								<p>No of Specialists</p>
							</div>
							<div class="icon">
								<i class="ion ion-person-add"></i>
							</div>
							<a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-6">
						<!-- small box -->
						<div class="small-box bg-danger">
							<div class="inner">
								<h3>${appointmentCount}</h3>

								<p>Total Number of Appointment</p>
							</div>
							<div class="icon">
								<i class="ion ion-pie-graph"></i>
							</div>
							<a href="#" class="small-box-footer">More info <i class="fas fa-arrow-circle-right"></i></a>
						</div>
					</div>
					<!-- ./col -->
				</div>



				<%----%>


				<%----%>

				<%----%>
				<div class="card card-danger">
					<div class="card-header">
						<h3 class="card-title">Donut Chart</h3>

						<div class="card-tools">
							<button type="button" class="btn btn-tool" data-card-widget="collapse"><i class="fas fa-minus"></i>
							</button>
							<button type="button" class="btn btn-tool" data-card-widget="remove"><i class="fas fa-times"></i></button>
						</div>
					</div>
					<div class="card-body">
						<canvas id="donutChart" style="height:230px; min-height:230px"></canvas>
					</div>
					<!-- /.card-body -->
				</div>
				<!-- /.card -->

				<!-- PIE CHART -->

				<!-- /.card -->

			</div>
				<%----%>

            </div>


            <!-- /.box-body -->


    </section>

</div>

<%@include file="fragments/footer.jsp" %>
<script>
	$(function () {
		getDoctorCountAndPatient();
	})

	var  doctor = new Array();
	var patient =  new Array();

	function getDoctorCountAndPatient(){
		var url = baseUrl + "admin/getBarGraph";

		$.post(url)
				.done(function(response) {
					console.log(response);

					if (response.success) {
						var items = response.body;

						if (items) {
							items.forEach(function(item) {
							patient.push(item.patientCount);
								doctor.push(item.doctorName);
							})
						}

					}

				})
				.fail(function(xhr, status, error) {
					alert("The Date and time is already booked");
				})
				.always(function() {

				})

	}

console.log(doctor);


	var ctx = document.getElementById('donutChart');
	var myChart = new Chart(ctx, {
		type: 'bar',
		data: {
			labels: doctor,
			datasets: [{
				label: '# of patients',
				data:patient,
				backgroundColor: [
					'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)',
					'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)',
					'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)'
				],
				borderColor: [
					'rgba(255, 99, 132, 1)',
					'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)',
					'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)',
					'rgba(255, 159, 64, 1)'
				],
				borderWidth: 1
			}]
		},
		options: {
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero: true
					}
				}]
			}
		}
	});





</script>