@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">
				<h4 class="center">Crear Recurso Virtual ({{ $activity->name }})</h4>				
				<br>
				<form method="POST" action="{{ route('recursos-virtuales.store', $activity->id) }}" enctype="multipart/form-data">
					{{csrf_field()}}
					<div class="row">
						<div class="input-field col s12">
					        <i class="material-icons prefix">format_align_left</i>
					        <input id="tittle" type="text" name="tittle" class="validate" required>
					        <label for="tittle">Título*</label>
						</div>
						<div class="input-field col s12">
					        <i class="material-icons prefix">reorder</i>
					        <textarea id="description" name="description" class="materialize-textarea validate" maxlength="255" required></textarea>
        					<label for="description">Descripción*</label>
						</div>

						<div class="input-field col s12">
					        <i class="material-icons prefix">movie</i>
					        <input id="video" type="text" name="video" class="validate">
					        <label for="video">Url Vídeo</label>
						</div>

						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Imágen</span>
								<input type="file" name="image" id="image">
							</div>
							<div class="file-path-wrapper col s5">
								<input class="file-path validate" type="text" name="image" placeholder="Selecciona la imágen para el recurso virtual">
							</div>
							<div class="col s5">
								<img id="resource_image_container" value="{{ isset($vresource)?$vresource->vresource:''}}" class="responsive-img">
							</div>
						</div>

						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Documento</span>
								<input type="file" name="docs" id="docs">
							</div>
							<div class="file-path-wrapper col s12">
								<input class="file-path validate" type="text" name="docs" placeholder="Selecciona el documento que llevará el recurso virtual.">
							</div>
						</div>

						<div class="col s12">
							<iframe id="document_container" value="{{ isset($normative)?$normative->normative:''}}" style="width: 100%; height: 600px; overflow-y: scroll;"></iframe>
						</div>	

				        <div class="center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la creación del recurso virtual?')">Cancelar</a>
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
          $('#resource_image_container').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      } 
    }

    $("#image").change(function() {
      readURL(this);
    });

    function readURL(input) {
      if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
          $('#document_container').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      } 
    }

    $("#docs").change(function() {
      readURL(this);
    });
</script>
@endsection()