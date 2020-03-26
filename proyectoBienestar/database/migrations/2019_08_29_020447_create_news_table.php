<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateNewsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('news', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('tittle');
            $table->longText('content');
            $table->string('image');
            // $table->integer('views')->default(0);

            $table->unsignedBigInteger('new_status_id');
            $table->foreign('new_status_id')
            ->references('id')
            ->on('new_statuses')
            ->onDelete('cascade');

            $table->unsignedBigInteger('area_id');
            $table->foreign('area_id')
            ->references('id')
            ->on('areas')
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
        Schema::dropIfExists('news');
    }
}
