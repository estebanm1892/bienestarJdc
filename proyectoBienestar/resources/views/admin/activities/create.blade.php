@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">
				<h4 class="center">CREAR ACTIVIDAD</h4>				
				<br>
				<form method="POST" action="{{ route('actividades.store') }}">
					{{csrf_field()}}
					<div class="row">
						<div class="input-field col s12">
					        <i class="material-icons prefix">format_align_left</i>
					        <input id="name" type="text" name="name" class="validate" required>
					        <label for="name">Nombre*</label>
						</div>
						<div class="input-field col s12">
					        <i class="material-icons prefix">reorder</i>
					        <textarea id="description" name="description" class="materialize-textarea validate" maxlength="255" required></textarea>
        					<label for="description">Descripción*</label>
						</div>
						<div class="input-field col s6">
							<i class="material-icons prefix">border_clear</i>
							<select id="area_id" name="area_id">
								<option value="" disabled selected>Selecciona el área de la actividad</option>
								@foreach ($areas as $area)
									@if (Auth::user()->user_type_id == 1)
										<option value="{{ $area->id }}">{{ $area->name }}</option>
									@elseif(Auth::user()->area_id == $area->id)
										<option value="{{ $area->id }}" {{ ($area->id === $area->id)? 'selected=selected': '' }}>{{ $area->name }}</option>
									@endif									
								@endforeach
							</select>
							<label>Área*</label>
						</div>
						<div class="input-field col s6">
							<i class="material-icons prefix">event_note</i>
					        <select multiple id="days" name="days[]">
					          	<option value="" disabled selected>Elige los días para la actividad</option>
					            @foreach($days  as $day)
					            <option value="{{ $day->id }}">{{ $day->name }}</option>
					            @endforeach            
					        </select>
					        <label>Días*</label>
					    </div>
					    <div class="input-field col s6">
							<i class="material-icons prefix">schedule</i>
					        <select id="initial_hour" name="initial_hour">
					          	<option value="" disabled selected>Hora de Inicio</option>
					            @foreach($hours  as $hour)
					            <option value="{{ $hour }}">{{ $hour }}</option>
					            @endforeach            
					        </select>
					        <label>Hora Inicial*</label>
					    </div>
					    <div class="input-field col s6">
							<i class="material-icons prefix">schedule</i>
					        <select id="final_hour" name="final_hour">
					          	<option value="" disabled selected>Hora de Culminación</option>
					            @foreach($hours  as $hour)
					            <option value="{{ $hour }}">{{ $hour }}</option>
					            @endforeach            
					        </select>
					        <label>Hora de Culminación*</label>
					    </div>
				        <div class="center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la creación de la actividad?')">Cancelar</a>
							<button class="btn light-blue darken-3" type="submit">Ingresar</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
@endsection()