@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">	
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">				
				<h4 class="center">CREAR NOTICIA</h4>
				<br>
				<form method="POST" action="{{ route('noticias.store') }}" enctype="multipart/form-data">
					{{csrf_field()}}
					<div class="row">
						<div class="input-field col s12">
							<div class="input-field col s12">
						        <i class="material-icons prefix">format_align_left</i>
						        <input id="tittle" type="text" name="tittle" class="validate" required>
						        <label for="tittle">Titulo*</label>
							</div>
						<div class="col s12">							
							<div class="input-field col s12">
								<h5>Contenido</h5>
								<textarea  class="textArea_content" id="content" name="content"></textarea>
							</div>
						</div>
						<div class="input-field col s6">
							<i class="material-icons prefix">border_clear</i>
							<select id="area_id" name="area_id">
								<option value="" disabled selected>Selecciona el área para la noticia</option>
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
							<i class="material-icons prefix">accessibility</i>
							<select id="new_status_id" name="new_status_id">
								<option value="" disabled selected>Selecciona el estado de la noticia</option>
								@foreach ($statuses as $status)
									<option value="{{ $status->id }}">{{ $status->name }}</option>
								@endforeach
							</select>
							<label>Estado*</label>
						</div>
						<br>
						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Imágen</span>
								<input type="file" name="image" id="image">
							</div>
							<div class="file-path-wrapper col s5">
								<input class="file-path validate" type="text" name="image" placeholder="Selecciona la imágen para la noticia." required>
							</div>
							<div class="col s12">
								<img id="image_container" value="{{ isset($publication)?$publication->image:''}}" class="responsive-img">
							</div>
						</div>
				        <div class="center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la creación de la noticia?')">Cancelar</a>
							<button class="btn light-blue darken-3" type="submit">Ingresar</button>
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
          $('#image_container').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      } 
    }

    $("#image").change(function() {
      readURL(this);
    });

    CKEDITOR.config.height = 200;
	CKEDITOR.config.extraPlugins = 'wordcount';
	CKEDITOR.replace('content');
	CKEDITOR.replace('objetive');
	CKEDITOR.replace('programs');
</script>
@endsection()