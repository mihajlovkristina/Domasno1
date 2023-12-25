import { createSlice } from "@reduxjs/toolkit";

const dataSlice = createSlice({
  name: "data",
  initialState: {
    places: [],
    cities: [],
    categories: [],
  },
  reducers: {
    addPlaces(state, action) {
      state.places = [...action.payload];
    },
    addCities(state, action) {
      state.cities = [...action.payload];
    },
    addCategories(state, action) {
      state.categories = [...action.payload];
    },
    updatePlace(state, action) {
      state.places = state.places?.map(place => {
        if (place.id === action.payload.id) {
          return {
            ...action.payload
          }
        }
        return {
          ...place
        }
      });
    },
    addPlace(state, action) {
      state.places = [action.payload, ...state.places]
    },
    deletePlace(state, action) {
      state.places = state.places?.filter((place) => place.id !== action.payload)
    }

  },

});
export default dataSlice.reducer;
export const dataActions = dataSlice.actions;