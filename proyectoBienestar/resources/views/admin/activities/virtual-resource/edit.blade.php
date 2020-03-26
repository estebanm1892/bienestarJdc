@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">
				<h4 class="center">Editar Recurso Virtual ({{ $vresource->tittle }})</h4>				
				<br>
				<form method="POST" action="{{ route('recursos-virtuales.update', $vresource->id) }}" enctype="multipart/form-data">
					{{csrf_field()}}
					{{ method_field('PUT') }}
					<div class="row">
						<div class="input-field col s12">
					        <i class="material-icons prefix">format_align_left</i>
					        <input id="tittle" type="text" name="tittle" class="validate" value="{{ $vresource->tittle }}"required>
					        <label for="tittle">Título*</label>
						</div>
						<div class="input-field col s12">
					        <i class="material-icons prefix">reorder</i>
					        <textarea id="description" name="description" class="materialize-textarea validate" maxlength="255" required>{{ $vresource->description }}</textarea>
        					<label for="description">Descripción*</label>
						</div>

						<div class="input-field col s12">
					        <i class="material-icons prefix">movie</i>
					        <input id="video" type="text" name="video" class="validate" value="{{ $vresource->video }}">
					        <label for="video">Url Vídeo</label>
					        @if (is_null($vresource->embed_video))
					        	<p></p>
					        	@else
					        	<div class="video-container">
					        		<iframe width="560" height="315" src="{{ $vresource->embed_video }}" frameborder="0" frameborder="0" allowfullscreen></iframe>
					        	</div>					        	
					        @endif					        
						</div>

						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Imágen</span>
								<input type="file" name="image" id="image" value="{{ $vresource->image }}">
							</div>
							<div class="file-path-wrapper col s5">
								<input class="file-path validate" type="text" name="image" placeholder="Selecciona la imágen para el recurso virtual">
							</div>
							<div class="col s5">
								<img id="resource_image_container" src="{{ isset($vresource)?$vresource->image:''}}" class="responsive-img">
							</div>
						</div>

						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Documento</span>
								<input type="file" name="docs" id="docs" value="{{ isset($vresource->docs)?$vresource->docs:'' }}">
							</div>
							<div class="file-path-wrapper col s12">
								<input class="file-path validate" type="text" name="docs" placeholder="Selecciona el documento que llevará el recurso virtual.">
							</div>							
						</div>
						@if (is_null($vresource->docs))
							<p></p>
							@else
							<div class="col s12">
								<iframe id="document_container" src="{{ $vresource->docs }}" style="width: 100%; height: 600px; overflow-y: scroll;"></iframe>
							</div>
						@endif
				        <div class="center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la edición del recurso virtual?')">Cancelar</a>
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