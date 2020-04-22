@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	@include('admin.layouts.partials._messages')
	<div class="card-panel">
		<div class="row">
			<h4 class="center">Preinscripciones ({{ $activity->name }})</h4>	
			<table>
				<thead>
					<tr>
						<th>Documento</th>
						<th>Nombre</th>
						<th>Email</th>
						<th>Semestre</th>
						<th>Programa acádemico</th>
					</tr>
				</thead>
				@foreach ($preregisters as $preregister)
					<tbody>
						<tr>
							<td>{{ $preregister->document }}</td>
							<td>{{ $preregister->name }}</td>
							<td>{{ $preregister->email }}</td>
							<td>{{ $preregister->semester }}</td>
							<td>{{ $preregister->academic_program }}</td>
						</tr>
					</tbody>
				@endforeach
			</table>
		</div>
	</div>
</div>
<?php $paginator = $preregisters; ?>
    @include('admin.layouts.partials._paginator')
@endsection()