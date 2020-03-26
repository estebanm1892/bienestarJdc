@extends('admin.layouts.admin_layout')
@section('content')
<div class="container">
@include('admin.layouts.partials._messages')
	<div class="card-panel">
		<div class="row">
			<h4 class="center">{{ $area->name }}</h4>
			<div class="col s12 m12 l12">			
				<div class="divider"></div>
			</div>
			<br>
			<div class="col s12 m6 l6">
				<img src="{{ $area->area_image }}" class="responsive-img">
			</div>
			<div class="col s12 m6 l6">
				<h5 class="center">Presentación del Area</h5>
				<div class="divider"></div>
				<label>{!! $area->area_presentation !!}</label>
			</div>
			<div class="col s12 m12 l12">
				<h5 class="ceneter">Objetivo</h5>
				<div class="divider"></div>
				<label>{!! $area->objetive !!}</label>
			</div>
			<div class="col s12 m12 l12">
				<h5 class="ceneter">Programa</h5>
				<div class="divider"></div>
				<label>{!! $area->programs !!}</label>
			</div>
			<div class="col s12 m12 l12">
				<h5 class="ceneter">Equipo de trabajo</h5>
				<div class="divider"></div>
				<br>
				@foreach ($users as $user)
					<div class="col s12 m6 l6">
						<img src="{{ isset($user)?$user->profile_image:'' }}" class="responsive-img">
						<div class="center">
							<label>{{ $user->name }}</label>
						</div>
					</div>
				@endforeach
			</div>
		</div>
	</div>
	<div class="center">
		@if (Auth::user()->user_type_id == 1)
			<a href="{{ route('areas.edit',$area->id) }}"><button class="btn light-blue darken-3" type="submit">Editar Área</button></a> 
		@elseif(Auth::user()->area_id == $area->id)
			<a href="{{ route('areas.edit',$area->id) }}"><button class="btn light-blue darken-3" type="submit">Editar Área</button></a> 
		@endif			
	</div>	
	<p></p>
	{{-- <a href="{{ route('areas.edit', $area->id) }}" class="btn-floating btn-large waves-effect waves-light right add-button"><i class="material-icons">add</i></a> --}}
</div>
@endsection()