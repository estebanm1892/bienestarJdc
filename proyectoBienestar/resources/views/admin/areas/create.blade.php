@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">	
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">				
				<h4 class="center">CREAR ÁREA</h4>
				<br>
				<form method="POST" action="{{ route('areas.store') }}" enctype="multipart/form-data">
					{{csrf_field()}}
					<div class="row">
						<div class="input-field col s12">
							<div class="input-field col s12">
						        <i class="material-icons prefix">format_align_left</i>
						        <input id="name" type="text" name="name" class="validate" required>
						        <label for="name">Nombre del área*</label>
							</div>
						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Imágen</span>
								<input type="file" name="area_image" id="area_image">
							</div>
							<div class="file-path-wrapper col s5">
								<input class="file-path validate" type="text" name="area_image" placeholder="Selecciona la imágen para el área." required>
							</div>
							<div class="col s5">
								<img id="area_image_container" value="{{ isset($area)?$area->area_image:''}}" class="responsive-img">
							</div>
						</div>
						<div class="col s12">							
							<div class="input-field col s12">
								<h5>Presentación del Área</h5>
								<textarea  class="textArea_content" id="area_presentation" name="area_presentation"></textarea>
							</div>
						</div>
						<div class="col s12">							
							<div class="input-field col s12">
								<h5>Objetivo</h5>
								<textarea  class="textArea_content" id="objetive" name="objetive"></textarea>
							</div>
						</div>
				        <div class="col s12">							
							<div class="input-field col s12">
								<h5>Programas</h5>
								<textarea  class="textArea_content" id="programs" name="programs"></textarea>
							</div>
						</div>
				        <div class="center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la creación del área?')">Cancelar</a>
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
          $('#area_image_container').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      } 
    }

    $("#area_image").change(function() {
      readURL(this);
    });

    CKEDITOR.config.height = 200;
	CKEDITOR.config.extraPlugins = 'wordcount';
	CKEDITOR.replace('area_presentation');
	CKEDITOR.replace('objetive');
	CKEDITOR.replace('programs');
</script>
@endsection()