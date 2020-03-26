<?php

namespace App\Http\Controllers;

use App\Area;
use App\User;
use Illuminate\Database\Eloquent\ModelNotFoundException;
use Illuminate\Http\Request;

class AreaController extends Controller
{

    private $menu_item = 2;
    private $title_page = 'Áreas';

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $areas = Area::orderBy('id','ASC')->get();
        return view('admin.areas.index', compact('areas'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    public function index_mobile()
    {
        $areas = Area::orderBy('name', 'ASC')
            ->select([
                'id',
                'name',
                'area_image'
            ])
            ->get();

        return response()->json([
            'areas'     =>  $areas,
            'success'   =>  true,
        ]);
    }

    public function show_mobile($id)
    {
        try {
            $area = Area::findOrfail($id);
        } catch (ModelNotFoundException $e) {
            return response()->json([
                'error'     =>  'No existe un área con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }

        $area = $area->where('id', $id)
            ->with(['publications' => function($publications){
                $publications->where('new_status_id', 2)
                ->orderBy('id', 'DESC')
                ->take(3)
                ->select([
                    'id',
                    'image',
                    'tittle',
                    'content',
                    'area_id'
                ]);
            }])
        ->select([
            'id',
            'name',
            'area_image',
            'area_presentation',
            'objetive',
            'programs'
        ])
        ->get();

        return response()->json([
            'area'     =>  $area,
            'success'   =>  true,
        ]);

    }

    public function show_mobile_information($id)
    {
        try {
            $area = Area::findOrfail($id);
        } catch (ModelNotFoundException $e) {
            return response()->json([
                'error'     =>  'No existe un área con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }

        $area = $area->where('id', $id)
            ->with(['users' => function($users){
                $users->select([
                    'id',
                    'name',
                    'profile_image',
                    'area_id'
                ]);
            }])
            ->with(['activities' => function($activities){
                $activities->select([
                    'id',
                    'name',
                    'area_id'
                ]);
            }])            
            ->select([
                'id',
                'name',
                'area_image',
                'area_presentation',
                'objetive',
                'programs'
            ])
            ->get();

        return response()->json([
            'area'     =>  $area,
            'success'   =>  true,
        ]);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('admin.areas.create')
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response     */
    public function store(Request $request)
    {
        $rules = [
            'name'              =>  'required|min:4',
            'area_image.*'      =>  'required|image|mimes:jpg,jpge,png',
            'area_presentation' =>  'required',
            'objetive'          =>  'required',
            'programs'          =>  'required'   
        ];

        $this->validate($request, $rules);

        $area = new Area();

        $area->name                 =   $request->input('name');
        $area->area_image           =   '';
        $area->area_presentation    =   $request->input('area_presentation');
        $area->objetive             =   $request->input('objetive');
        $area->programs             =   $request->input('programs');

        $area->save();

        if ($request->file('area_image')) {
            $file       =   $request->file('area_image');
            $nameImg    =   'bienestar_area'.$area->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/areas/';
            $file->move($path, $nameImg);

            $area->area_image = '/img/areas/'.$nameImg;
            $area->save();
        }

        return redirect()->route('areas.index')->with('session_msg', 'Se ha creado el área correctamente.');

    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $area    = Area::findOrfail($id);
        $users   = User::where('user_status_id', 1)->where('area_id', $area->id)->orderBy('id', 'ASC')->get();

        

        return view('admin.areas.show', compact('area'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item)
        ->with('users', $users);

    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        $area   =   Area::findOrfail($id);

        return view ('admin.areas.edit', compact('area'))
        ->with('title_page', $this->title_page)
        ->with('menu_item', $this->menu_item);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
         $rules = [
            'name'              =>  'required|min:4',
            'area_image.*'      =>  'image|mimes:jpg,jpge,png',
            'area_presentation' =>  'required',
            'objetive'          =>  'required',
            'programs'          =>  'required'   
        ];

        $this->validate($request, $rules);

        $area = Area::find($id);

        $area->name                 =   $request->input('name');
        $area->area_presentation    =   $request->input('area_presentation');
        $area->objetive             =   $request->input('objetive');
        $area->programs             =   $request->input('programs');

        $area->save();

        if ($request->file('area_image')) {
            $file       =   $request->file('area_image');
            $nameImg    =   'bienestar_area'.$area->id.'_'.time().'.'.$file->getClientOriginalExtension();
            $path       =   public_path().'/img/areas/';
            $file->move($path, $nameImg);

            $area->area_image = '/img/areas/'.$nameImg;
            $area->save();
        }

        return redirect()->route('areas.index')->with('session_msg', 'Se ha editado el área correctamente.');
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
