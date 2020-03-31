@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	@include('admin.layouts.partials._messages')
	<div class="card-panel">
		<div class="row">	
			<table>
		        <thead>
		         	<tr>
		              <th></th>
		              <th>Nombre</th>
		              <th>Opciones</th>
		          	</tr>
		        </thead>
		        @foreach ($areas as $area)
			        <tbody>
			          	<tr>
				            <td>
				            	<div class="col s2">
				              		<img src="{{ $area->area_image }}" alt="" class="profile-picture-cms profile_img_view left"> <!-- notice the "circle" class -->
				            	</div>
							</td>
				            <td>{{ $area->name }}</td>
				            <td>
				            	@if (Auth::user()->user_type_id == 1)
				            		<a href="{{ route('areas.show', $area->id) }}"><span class="links-est">Ver</span><i class="material-icons left icon-blue">remove_red_eye</i></a>
							        <p></p>    	
					            	<a href="{{ route('areas.edit', $area->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
							        <p></p>
							   	@elseif(Auth::user()->area_id == $area->id)
							   		<a href="{{ route('areas.show', $area->id) }}"><span class="links-est">Ver</span><i class="material-icons left icon-blue">remove_red_eye</i></a>
							        <p></p>    	
					            	<a href="{{ route('areas.edit', $area->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
					            @elseif(Auth::user()->area_id != $area->id)
					            	<a href="{{ route('areas.show', $area->id) }}"><span class="links-est">Ver</span><i class="material-icons left icon-blue">remove_red_eye</i></a>
				            	@endif
				            </td>
			          	</tr>
			        </tbody>
				@endforeach
	      	</table>
		</div>
	</div>
	{{-- ESTO ESTA COMENTADO YA QUE AL MOMENTO DE CREAR LAS ÁREAS, EL MIDDLEWARE TOMARÁ EL USUARIO CON EL ÁREA RELACIONADA PARA PODER REDIRECCIONARLO, SI SE CREA OTRA ÁREA, SE TENDRÍA QUE MODIFICAR NUEVAMENTE EL MIDDLEWARE --}}
	{{-- <a href="{{ route('areas.create') }}" class="btn-floating btn-large waves-effect waves-light right add-button"><i class="material-icons">add</i></a> --}}
</div>
@endsection()