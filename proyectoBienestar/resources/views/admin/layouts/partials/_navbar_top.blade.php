<div class="container" style="width: 80%">
    <div class="row">
        <div class="col s12 m4 l4">
            <h5>{{$title_page}}</h5>
        </div>
        <div class="col s12 m8 l8" style="padding-top: 22px">
             <!-- Dropdown Trigger -->
             <!-- Dropdown Trigger -->
              {{-- <a class='dropdown-trigger right' href='#' data-target='dropdown1'><i class="material-icons left dropdown-admin-icon-left">person</i>
                        {!! str_limit(Auth::user()->name, 16) !!}
                        <i class="material-icons right dropdown-admin-icon-right">arrow_drop_down</i></a> --}}

              <!-- Dropdown Structure -->
              <ul id='dropdown1' class='dropdown-content'>
                <li><a href="#!">one</a></li>
                <li><a href="#!">two</a></li>
                <li class="divider" tabindex="-1"></li>
                <li><a href="#!">three</a></li>
                <li><a href="#!"><i class="material-icons">view_module</i>four</a></li>
                <li><a href="#!"><i class="material-icons">cloud</i>five</a></li>
              </ul>
        </div>
    </div>
</div>

<style type="text/css">
    a {
    color: #000;
    text-decoration: none;
    -webkit-tap-highlight-color: transparent;
}
</style>