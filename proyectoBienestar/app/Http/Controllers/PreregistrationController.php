<?php

namespace App\Http\Controllers;

use App\Activity;
use App\Preregistration;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Database\Eloquent\ModelNotFoundException;

class PreregistrationController extends Controller
{
    private $menu_item = 5;
    private $title_page = 'Preinscripciones';

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index($id)
    {
        // 
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request, $id)
    {   
                
        try {
            $activity = Activity::findOrfail($id);
        } catch (ModelNotFoundException $e) {
            return response()->json([
                'error'     =>  'No existe una actividad con Id: '.$id,
                'success'   =>  false,
            ], 404);
        }

        $rules = [
            'document'              =>  'required|numeric' ,
            'name'                  =>  'required|min:3',
            'email'                 =>  'required|email',
            'semester'              =>  'required',
            'phone'                 =>  'required',
            'academic_program'      =>  'required'
        ];

        $credentials = $request->only(
            'document',
            'name',
            'email',
            'semester',      
            'phone',      
            'academic_program',
        );

        $validator = Validator::make($credentials, $rules);

        if ($validator->fails()) {
            $error = $validator->messages()->toJson();
            return response()->json([
                'error'     =>  $error,
                'success'   =>  false,
            ]);
        }

        $preregister = new Preregistration();
        $preregister->fill($request->all());
        $preregister->readed = false;
        $preregister->activity_id = $activity->id;
        $preregister->save();

        return response()->json($preregister, 201)->setEncodingOptions(JSON_NUMERIC_CHECK);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        // 
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
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
        //
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
