@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	@include('admin.layouts.partials._messages')
	<div class="card-panel">
		<div class="row">
			<table>
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Área</th>
						<th>Día</th>
						<th>Hora de Inicio</th>
						<th>Hora de Culminación</th>
						<th>Opciones</th>
					</tr>
				</thead>
				@foreach ($activities as $activity)
					<tbody>
						<tr>
							<td>{{ $activity->name }}</td>
							<td>{{ $activity->area->name }}</td>
							<td>
								@foreach ($activity->days as $day)
									{{ $day->name }}
								@endforeach
							</td>
							<td>{{ $activity->initial_hour }}</td>
							<td>{{ $activity->final_hour }}</td>
							<td>
								@if (Auth::user()->user_type_id == 1)
									<a href="{{ route('actividades.edit', $activity->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
					            	<p></p>
					            	<a method="POST" href="{{ route('actividades.destroy', $activity->id) }}" {{csrf_token()}}  onclick="return confirm('¿Deseas eliminar la actividad?')"><span class="links-est">Eliminar</span><i class="material-icons left icon-blue">delete</i></a>
					            	<p></p>
					            	@if (App\Preregistration::where('activity_id', $activity->id)->where('readed', false)->count())
					            		<form action="{{ route('actividades.preregisters', $activity->id) }}">
					            			<button class="btn red orange darken-3 status_button" style="font-size: 7px"><b>Preinscripciones</b></button>
					            		</form>
				            		@else
				            			<form action="{{ route('actividades.preregisters', $activity->id) }}">
				            				<button class="btn green status_button" style="font-size: 7px"><b>Preinscripciones</b></button>	
				            			</form>
					            	@endif
					            @elseif(Auth::user()->area_id == $activity->area_id)
					            	<a href="{{ route('actividades.edit', $activity->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
					            	<p></p>
					            	<a method="POST" href="{{ route('actividades.destroy', $activity->id) }}" {{csrf_token()}}  onclick="return confirm('¿Deseas eliminar la actividad?')"><span class="links-est">Eliminar</span><i class="material-icons left icon-blue">delete</i></a>
					            	<p></p>
					            	@if (App\Preregistration::where('activity_id', $activity->id)->where('readed', false)->count())
					            		<form action="{{ route('actividades.preregisters', $activity->id) }}">
					            			<button class="btn red orange darken-3 status_button" style="font-size: 7px"><b>Preinscripciones</b></button>
					            		</form>
				            		@else
				            			<form action="{{ route('actividades.preregisters', $activity->id) }}">
				            				<button class="btn green status_button" style="font-size: 7px"><b>Preinscripciones</b></button>	
				            			</form>
					            	@endif
								@endif				            	
				            </td>
						</tr>
					</tbody>
				@endforeach
			</table>
		</div>
	</div>
	<?php $paginator = $activities; ?>
    @include('admin.layouts.partials._paginator')
	<a href="{{ route('actividades.create') }}" class="btn-floating btn-large waves-effect waves-light right add-button"><i class="material-icons">add</i></a>
</div>
@endsection()