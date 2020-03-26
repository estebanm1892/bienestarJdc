<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
		me='viewport' />		
    <meta name="viewport" content="width=device-width" />
    <meta name="google" value="notranslate">
    <meta name="_token" content="{{ csrf_token() }}" />
	{{-- <link rel="icon" type="image/png" sizes="96x96" href="{{ asset('/img/icon.png') }}"> --}}
	<link href="{{ asset('css/materialize.min.css') }}" rel="stylesheet"/>
    <link href="{{ asset('css/style.css') }}" rel="stylesheet" /> 
    <link href='https://fonts.googleapis.com/css?family=Roboto:400,700,300|Material+Icons' rel='stylesheet' type='text/css'>
    @yield('imported_css')
	<title>Bienestar JDC</title>
</head>
<body>
	@include('login.layouts.partials._navbar')	
	<main>
		@yield('content')
	</main>
    @include('login.layouts.partials._footer')
</body>
	<script src="{{ asset('js/jquery.js') }}"></script>	
    <script src="{{ asset('js/materialize.js') }}"></script>
    <script src="{{ asset('js/materialize.min.js') }}"></script>
    @yield('imported_js')
    <script type="text/javascript">    	
         $('.tooltipped').tooltip({delay: 50});
        $("#session_msg").delay(3000).hide(600);
        $('.modal').modal();
        $('.materialboxed').materialbox();
        $(document).ready(function(){
            $('.sidenav').sidenav();
            $('#sidenav-1').sidenav({ edge: 'left' });
            $('select').formSelect();
        });
        $(".dropdown-trigger").dropdown();
    </script>
    @yield('js')
</html>