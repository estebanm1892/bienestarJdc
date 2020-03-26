@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">	
	<div class="row">
		@include('admin.layouts.partials._messages')
		<div class="col s12 m12 l12">
			<div class="card-panel">				
				<h4 class="center">EDITAR NORMATIVA</h4>
				<br>
				<form method="POST" action="{{ route('normativas.update', $normative->id) }}" enctype="multipart/form-data">
					{{csrf_field()}}
					{{ method_field('PUT') }}
					<div class="row">
						<div class="input-field col s12">
							<div class="input-field col s12">
						        <i class="material-icons prefix">format_align_left</i>
						        <input id="tittle" type="text" name="tittle" class="validate" value="{{ $normative->tittle }}" required>
						        <label for="tittle">Titulo*</label>
							</div>

						<div class="file-field input-field col s12">
							<div class="btn-large actions-button col s2">
								<span>Documento</span>
								<input type="file" name="document" id="document" value="{{ isset($normative->document)?$normative->document:'' }}">
							</div>
							<div class="file-path-wrapper col s12">
								<input class="file-path validate" type="text" name="document" placeholder="Selecciona el documento que llevará la normativa.">
							</div>
						</div>

						<div class="col s12">
							<iframe id="document_container" src="{{ $normative->document }}" style="width: 100%; height: 600px; overflow-y: scroll;"></iframe>
						</div>	
											
				        <div class="center">
							<a href="{{ url()->previous() }}" class="btn grey" onclick="return confirm('¿Deseas cancelar la edición de la normativa?')">Cancelar</a>
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
          $('#document_container').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
      } 
    }

    $("#document").change(function() {
      readURL(this);
    });
</script>
@endsection()