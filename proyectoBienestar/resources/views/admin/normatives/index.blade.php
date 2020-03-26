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
						<th>Opciones</th>
					</tr>
				</thead>
				@foreach ($normatives as $normative)
					<tbody>
						<tr>
							<td>{{ $normative->tittle }}</td>
							<td>
	    						<a href="{{ route('normativas.edit', $normative->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
				            	<p></p>
				            	<a method="POST" enctype="multipart/form-data" href="{{ route('normativas.destroy', $normative->id) }}" {{csrf_token()}} onclick="return confirm('¿Deseas eliminar la noticia?')"><span class="links-est">Eliminar</span><i class="material-icons left icon-blue">delete</i></a>
	    					</td>
						</tr>
					</tbody>
				@endforeach
			</table>			
		</div>
	</div>
	<?php $paginator = $normatives; ?>
    @include('admin.layouts.partials._paginator')
	<div class="col s12">
		<a href="{{ route('normativas.create')}}" class="btn-floating btn-large waves-effect waves-light right add-button"><i class="material-icons">add</i></a>
	</div>
</div>
@endsection()