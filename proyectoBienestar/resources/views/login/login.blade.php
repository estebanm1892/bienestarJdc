@extends('login.layouts.login_layout')
@section('content')
<div class="container">
	<div class="center">
		@include('admin.layouts.partials._messages')
		<div class="row">
			<div class="col s12 m4 l2"><p></p>
			</div>			
		    <div class="col s12 m4 l8"><p>
		    	<div class="card-panel panel-login">
		    		<h4 class="tittle-card-login">Bienestar JDC</h4>
		    		<p class="divider"></p>	
		    		<form method="POST" action="{{ route('login') }}">
		    			@csrf
		    			<div class="input-field col s12">
	                      <i class="material-icons prefix">email</i>
                          <input id="email" type="email" class="form-control @error('email') is-invalid @enderror" name="email" value="{{ old('email') }}"required>
	                      <label for="email">Correo Electrónico</label>
                          @error('email')
                            <span class="invalid-feedback" role="alert">
                                <strong>{{ $message }}</strong>
                            </span>
                          @enderror
                    	</div> 
                    	<div class="input-field col s12">
	                      <i class="material-icons prefix">lock</i>
	                      <input id="password" type="password" class="form-control @error('password') is-invalid @enderror" name="password" required>
	                      <label for="password">Contraseña</label>
	                        @error('password')
                            <span class="invalid-feedback" role="alert">
                                <strong>{{ $message }}</strong>
                            </span>
                        @enderror
                    	</div>
                    	<div class="center">
	                        <button class="btn light-blue darken-3"  type="submit"> {{ __('INGRESAR') }}
	                        </button>
                    	</div>
                    	<br>                    
	                    @if(session()->has('message'))
	                    <div class="alert alert-success center">
	                        {{ session()->get('message') }}
	                    </div>
	                     @endif	
		    		</form>		
		    	</div>		    	
		    	</p>
			</div>
		    <div class="col s12 m4 l2"><p></p>
		    </div>
		</div>
	</div>
</div>
@endsection()