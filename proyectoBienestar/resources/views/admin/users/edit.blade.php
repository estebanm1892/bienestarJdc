@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">
				<h4 class="center">EDITAR USUARIO</h4>
				<br>
				<form method="POST" action="{{ route('usuarios.update', $user->id) }}" enctype="multipart/form-data">
					{{csrf_field()}}
					{{ method_field('PUT') }}
					<div class="row">
						<div class="input-field col s12">
					        <i class="material-icons prefix">format_align_left</i>
					        <input id="name" type="text" name="name" class="validate" value="{{ $user->name }}" required>
					        <label for="name">Nombre del usuario*</label>
						</div>
						{{-- <div class="input-field col s6">
					        <i class="material-icons prefix">email</i>
					        <input id="email" type="email" name="email" class="validate" value="{{ $user->email }}" required>
					        <label for="email">Correo Electrónico*</label>
						</div> --}}
						{{-- <div class="input-field col s6">
					        <i class="material-icons prefix">lock</i>
					        <input id="password" type="password" name="password" class="validate" required>
					        <label for="password">Contraseña*</label>
						</div> --}}
						<div class="input-field col s6">
							<i class="material-icons prefix">border_clear</i>
							<select id="area_id" name="area_id">
								<option value="" disabled selected>Selecciona el área para el usuario</option>
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
							<i class="material-icons prefix">accessibility</i>
							<select id="user_status_id" name="user_status_id">
								<option value="" disabled selected>Selecciona el estado para el usuario</option>
								@foreach ($statuses as $status)
									@if (Auth::user()->user_type_id == 1)
										<option value="{{ $status->id }}" {{ ($status->id===$myStatus->id)?'selected=selected':'' }}>{{ $status->name }}</option>
									@elseif(Auth::user()->user_status_id == $status->id)
										<option value="{{ $status->id }}" {{ ($status->id === $status->id)? 'selected=selected': '' }}>{{ $status->name }}</option>
									@endif									
								@endforeach
							</select>
							<label>Estado*</label>
						</div>
						<br>
						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Imágen</span>
								<input type="file" name="profile_image" id="profile_image" value="{{ $user->profile_image }}">
							</div>
							<div class="file-path-wrapper col s5">
								<input class="file-path validate" type="text"  placeholder="Selecciona la imágen para el usuario">
							</div>
							<div class="col s5">
								<img id="profile_image_container" src="{{ isset($user)?$user->profile_image:''}}" class="responsive-img">
							</div>
						</div>
						<div class="col s12 center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la edición del usuario?')">Cancelar</a>
							<button class="btn light-blue darken-3" type="submit">Editar</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
@endsection()

@section('js')
<script type="text/javascript">
	function readURL(input) {
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
          $('#profile_image_container').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      } 
    }

    $("#profile_image").change(function() {
      readURL(this);
    });
</script>
@endsection()