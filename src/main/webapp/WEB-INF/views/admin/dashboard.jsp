<%@ include file="../fragments/header.jsp" %>

<style>
	.content-wrapper{position: relative;}
	.small-info-circle{display: inline-block; text-align: center;}
	.small-info-circle .img-container{position: relative; overflow: hidden; border-radius: 50%}
	.img-container>span{position: absolute; bottom: -18px; left: calc(50% - 27px); width: 50%; height: 50%; line-height: 40px; font-size: 20px; font-weight: bold; color: #fff; background: #2874f0; border-radius: 50%}
	.info-student{position: absolute; bottom: 25%; left: 10%}
	.info-employee{position: absolute; bottom: 50%; left: 22%}
	.info-parent{position: absolute; top: 20%; left: 40%}
	.content-wrapper{background: #fff}
	.content-wrapper{background: url('${baseUrl}/resources/images/hero-area.svg') no-repeat scroll 0 100% transparent; background-size: cover;}
	.digitalize-mob{position: absolute; bottom: 20%; right: 3%}
	.main-header{box-shadow: 0 0 3px rgba(0,0,0,0.2); background: #fff}
</style>



<!-- backup at sample_report.jsp -->

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>Welcome back, ${sessionScope.admin_name}</h1>

	</section>

	<!-- Main content -->
	<section class="content container-fluid">
		${message}
		<img src="${baseUrl}/resources/images/intro-mobile.png" class="img-responsive digitalize-mob" width="400" alt="Digitalize">
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@ include file="../fragments/footer.jsp" %>