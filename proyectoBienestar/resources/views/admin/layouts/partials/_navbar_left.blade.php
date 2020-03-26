<ul id="sidenav-1" class="sidenav sidenav-fixed">
  <div class="background home-side">
    <a href="{{ route('home') }}">
      <img class="responsive-img" src="{{ asset('img/logo.png') }}">      
    </a>
  </div>
  {{-- <li><a class="subheader">Always out except on mobile</a></li> --}}  
  <li><a href="{{ route('home') }}" {{ $menu_item == 1 ? 'class=active-est' : '' }}><i class="small material-icons">person</i>Usuarios</a></li>
  <li class="divider"></li>
  <li><a href="{{ route('areas.index') }}" {{ $menu_item == 2 ? 'class=active-est' : '' }}><i class="small material-icons">border_clear</i>Áreas</a></li>
  <li><a href="{{ route('actividades.index') }}" {{ $menu_item == 3 ? 'class=active-est' : '' }}><i class="small material-icons">receipt</i>Actividades</a></li>
  <li><a href="{{ route('noticias.index') }}" {{ $menu_item == 4 ? 'class=active-est' : '' }}><i class="small material-icons">library_books</i>Noticias</a></li>
  {{-- <li><a href="{{ route('preinscripciones.index') }}" {{ $menu_item == 5 ? 'class=active-est' : '' }}><i class="small material-icons">forum</i>Preinscripciones</a></li> --}}
  <li><a href="{{ route('normativas.index') }}" {{ $menu_item == 6 ? 'class=active-est' : '' }}><i class="small material-icons">assignment</i>Normativa</a></li>
{{--   <li><a href="https://github.com/dogfalo/materialize/" target="_blank"><i class="small material-icons">calendar_today</i>Programación</a></li> --}}
  <li class="divider"></li>
  <li><a href="{{ route('logout') }}" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">
        <i class="material-icons">exit_to_app</i>Cerrar Sesión
      </a>
      <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
        {{ csrf_field() }}
      </form></li>
</ul>


{{-- ESTILOS DEL SIDENAV --}}

<style type="text/css">header, main, footer {
  padding-left: 250px;
}

@media only screen and (max-width : 992px) {
  header, main, footer {
    padding-left: 0;
  }
}

.home-side{
  background: #403E3E
}

.sidenav li>a {
    color: rgba(0,0,0,0.87);
    display: block;
    font-size: 22px;
    font-weight: 500;
    height: 48px;
    line-height: 48px;
    padding: 0 32px;
}</style>

