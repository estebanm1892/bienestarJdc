@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
	@include('admin.layouts.partials._messages')
	<div class="card-panel">
		<table>
	        <thead>
	          <tr>
	              <th>Datos</th>
	              <th>Estado</th>
	              <th>Opciones</th>
	          </tr>
	        </thead>
		        <tbody>
	        @foreach ($users as $user)
		         <tr>
		            <td>
		            	<div class="col s12 m8 offset-m2 l6 offset-l3">
				         	<div class="row valign-wrapper">
				            	<div class="col s2">
				              		<img src="{{ $user->profile_image }}" alt="" class="profile-picture-cms profile_img_view left"> <!-- notice the "circle" class -->
				            	</div>
				            	<div class="col s8">
				              		<span class="black-text">
				                		{{$user->name}}
				              		</span>
				              		<br>
				              		<span class="black-text">
				                		{{ $user->email }}
				              		</span>
				              		<br>
				              		<span class="black-text">
				                		<b>Area:</b>
				              		</span>
				              		<span class="black-text">
				                		{{ $user->area->name }}
				              		</span>
				            	</div>
				          	</div>
					    </div>
		            </td>
		            <td>
		            	<div class="col s12 m8 offset-m2 l6 offset-l3">
		            		<div class="row valign-wrapper">
		            			<div class="col s12">
		            				@if ($user->user_status_id==1)
		            					<button class="btn green status_button" style="width: 102px;"><b>ACTIVO</b></button>	
		            					@else
		            					<button class="btn red status_button"><b>INACTIVO</b></button>	
		            				@endif
		            			</div>
		            		</div>	            		
		            	</div>
		            </td>
		            <td>
		            	@if (Auth::user()->user_type_id == 1)
		            		<a href="{{ route('usuarios.edit', $user->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
			            	<p></p>
			            	<a method="POST" href="{{ route('home.usuario.destroy', $user->id) }}" {{csrf_token()}} onclick="return confirm('¿Deseas eliminar el usuario?')"><span class="links-est">Eliminar</span><i class="material-icons left icon-blue">delete</i></a>
			            @elseif(Auth::user()->id == $user->id )
		            		<a href="{{ route('usuarios.edit', $user->id) }}"><span class="links-est">Editar</span><i class="material-icons left icon-blue">edit</i></a>
		            	@endif		            	
		            </td>
	          	</tr>
		        </tbody>
	        @endforeach
     	</table>
	</div>
	<?php $paginator = $users; ?>
    @include('admin.layouts.partials._paginator')
	@if (Auth::user()->user_type_id == 1)
		<a href="{{ route('usuarios.create') }}" class="btn-floating btn-large waves-effect waves-light right add-button"><i class="material-icons">add</i></a>		
	@endif
</div>
@endsection()