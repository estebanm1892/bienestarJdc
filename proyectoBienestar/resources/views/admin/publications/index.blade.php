@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	@include('admin.layouts.partials._messages')
	<div class="card-panel">
		<div class="row">
			<table>
				<thead>
					<tr>
						<th>Titulo</th>
						<th>Área</th>
						<th>Estado</th>
						<th>Opciones</th>
					</tr>
				</thead>
				@foreach ($publications as $publication)
					<tbody>
						<tr>
							<td>{{ $publication->tittle }}</td>
							<td>{{ $publication->area->name }}</td>
							<td>	            			
	            				@if ($publication->new_status_id==2)
	            					<button class="btn green status_button"><b>Publicada</b></button>	
	            					@else
	            					<button class="btn red status_button"><b>Pendiente</b></button>	
	            				@endif
	            			</td>
							<td>
	    						<a href="{{ route('noticias.edit', $publication->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
				            	<p></p>
				            	<a method="POST" enctype="multipart/form-data" href="{{ route('noticias.destroy', $publication->id) }}" {{csrf_token()}} onclick="return confirm('¿Deseas eliminar la noticia?')"><span class="links-est">Eliminar</span><i class="material-icons left icon-blue">delete</i></a>
	    					</td>
						</tr>
					</tbody>
				@endforeach
			</table>
		</div>
	</div>
	<?php $paginator = $publications; ?>
    @include('admin.layouts.partials._paginator')
	<div class="col s12">
		<a href="{{ route('noticias.create')}}" class="btn-floating btn-large waves-effect waves-light right add-button"><i class="material-icons">add</i></a>
	</div>
</div>
@endsection()