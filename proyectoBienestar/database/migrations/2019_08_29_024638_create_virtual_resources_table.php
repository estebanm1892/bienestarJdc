<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateVirtualResourcesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('virtual_resources', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('tittle');
            $table->text('description');
            $table->string('docs')->nullable();
            $table->string('video')->nullable();
            $table->string('image')->nullable();

            $table->unsignedBigInteger('activity_id');
            $table->foreign('activity_id')
            ->references('id')
            ->on('activities')
            ->onDelete('cascade');  
            
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('virtual_resources');
    }
}
