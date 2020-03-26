@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">
				<h4 class="center">EDITAR ACTIVIDAD</h4>				
				<br>
				<form method="POST" action="{{ route('actividades.update', $activity->id) }}">
					{{csrf_field()}}
					{{ method_field('PUT') }}
					<div class="row">
						<div class="input-field col s12">
					        <i class="material-icons prefix">format_align_left</i>
					        <input id="name" type="text" name="name" class="validate" value="{{ $activity->name }}" required>
					        <label for="name">Nombre*</label>
						</div>
						<div class="input-field col s12">
					        <i class="material-icons prefix">reorder</i>
					        <textarea id="description" name="description" class="materialize-textarea validate" maxlength="255" required>{{ $activity->description }}</textarea>
        					<label for="description">Descripción*</label>
						</div>
						<div class="input-field col s6">
							<i class="material-icons prefix">border_clear</i>
							<select id="area_id" name="area_id">
								<option value="" disabled selected>Selecciona el área para la actividad</option>
								@foreach ($areas as $area)
									@if (Auth::user()->user_type_id == 1)
										<option value="{{ $area->id }}" {{ ($area->id===$myArea->id)? 'selected=selected' : '' }}>{{ $area->name }}</option>
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
					            <option value="{{ $day->id }}" {{ in_array($day->id, $myDays)? 'selected==selected' : '' }}>{{ $day->name }}</option>
					            @endforeach            
					        </select>
					        <label>Días*</label>
					    </div>
					    <div class="input-field col s6">
							<i class="material-icons prefix">schedule</i>
					        <select id="initial_hour" name="initial_hour">
					          	<option value="" disabled selected>Hora de Inicio</option>
					            @foreach($hours  as $hour)
					            <option value="{{ $hour }}" {{ ($activity->initial_hour===$hour)?'selected=selected' : '' }}>{{ $hour }}</option>
					            @endforeach            
					        </select>
					        <label>Hora Inicial*</label>
					    </div>
					    <div class="input-field col s6">
							<i class="material-icons prefix">schedule</i>
					        <select id="final_hour" name="final_hour">
					          	<option value="" disabled selected>Hora de Culminación</option>
					            @foreach($hours  as $hour)
					            <option value="{{ $hour }}" {{ ($activity->final_hour===$hour)?'selected=selected' : '' }}>{{ $hour }}</option>
					            @endforeach            
					        </select>
					        <label>Hora de Culminación*</label>
					    </div>
					    <div class="center">
					    	<h5>Recursos Virtuales</h5>
					    	<br>
					    	@if ($resources->isEmpty())
					    		<span>No hay recursos virtuales asignados a esta actividad.</span>
					    	@else					    	
					    		<table>
					    			<thead>
					    				<tr>
					    					<th>Titulo</th>
					    					<th>descripción</th>
					    					<th>Vídeo</th>
					    					<th>Documento</th>
					    					<th>Imágen</th>
					    					<th>Opciones</th>
					    				</tr>
					    			</thead>
					    			@foreach ($resources as $resource)
					    				<tr>
					    					<td>{{ str_limit($resource->tittle, 25) }}</td>
					    					<td>{{ str_limit($resource->description, 25) }}</td>
					    					<td>
					    						@if (is_null($resource->video))
					    							No
					    						@else
					    							Si
					    						@endif
					    					</td>
					    					<td>
					    						@if (is_null($resource->docs))
					    							No
					    						@else
					    							Si
					    						@endif
					    					</td>
					    					<td>
					    						@if (is_null($resource->image))
					    							No
					    						@else
					    							Si
					    						@endif
					    					</td>
					    					<td>
					    						<a href="{{ route('recursos-virtuales.edit', $resource->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
								            	<p></p>
								            	<a method="POST" enctype="multipart/form-data" href="{{ route('recursos-virtuales.destroy', $resource->id) }}" {{csrf_token()}} onclick="return confirm('¿Deseas eliminar el recurso virtual?')"><span class="links-est">Eliminar</span><i class="material-icons left icon-blue">delete</i></a>
					    					</td>
					    				</tr>
					    			@endforeach
					    		</table>					    	
					    	@endif
					    	<br>
					    	<div class="col s12">
					    		<a href="{{ route('recursos-virtuales.create', $activity->id) }}" class="btn-floating btn-large waves-effect waves-light right add-button"><i class="material-icons">add</i></a>
					    	</div>
					    </div>
					    <br>
				        <div class="center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la edición de la actividad?')">Cancelar</a>
							<button class="btn light-blue darken-3" type="submit">Editar</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
@endsection()